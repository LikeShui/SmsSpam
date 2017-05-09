package com.ouaspel.anthony.smsfiltering;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static com.ouaspel.anthony.smsfiltering.Officer.officerList;

/**
 * Created by alna173017 on 5/4/2017.
 */

public class Receiver extends BroadcastReceiver {
    private static final int REPORT = 0;
    private static final int NO_REPORT = 1;
    private static final int ERROR = 2;
    private final SmsManager manager = SmsManager.getDefault();
    private SmsMessage currentMessage;
    private final static String[] RESPONSE = {"Thank you for sending a report.", "The system will mark down for not having a report", "Hey dumbass, you didn't type a command correctly. It's /report or /noreport with nothing extra."};

    @Override
    public void onReceive(Context c, Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                String message = "", number = "";
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int j = 0; j < pdusObj.length; j++) {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[j]);
                    number = Main.MANAGER.getLine1Number();
                    message = currentMessage.getDisplayMessageBody();
                }

                handleMessage(number, message);
            }
        } catch (NullPointerException e) {
            Toast.makeText(c, "Null pointer exception occurred. Logcat should catch.", Toast.LENGTH_SHORT).show();
            Log.d("Stack trace", "" + e.getCause());
        } catch (Exception e) {
            Toast.makeText(c, "Unhandled exception occurred. Logcat should catch.", Toast.LENGTH_LONG).show();
            Log.d("Stack trace", "" + e.getCause());
        }
    }

    private void handleMessage(String number, String message) {
        for (int i = 0; i < Officer.LENGTH; i++) {
            if (officerList[i].numberIs(number)) {
                int result = parse(message);
                switch (result) {
                    case NO_REPORT:
                        officerList[i].report = false;
                        officerList[i].responded = true;
                        manager.sendTextMessage(number, null, RESPONSE[result], null, null);
                        break;
                    case REPORT:
                        officerList[i].report = true;
                        officerList[i].responded = true;
                        manager.sendTextMessage(number, null, RESPONSE[result], null, null);
                        break;
                    case ERROR:
                        manager.sendTextMessage(number, null, RESPONSE[result], null, null);
                    default:
                        break;
                }
                break;
            }
        }
    }


    private int parse(String message) {
        if (message.toLowerCase().trim().equals("/noreport"))
            return NO_REPORT;
        else if (message.toLowerCase().trim().equals("/report"))
            return REPORT;
        else if (message.substring(0, 1).equals("/"))
            return ERROR;
        else
            return -1;
    }
}