package com.ouaspel.anthony.smsfiltering;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This is supposed to schedule the text message sending. It doens't. Future implementation maybe.
 */

public class Spam {//extends BroadcastReceiver {
    private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        int n = Receiver.reports.length;
//        for(int i = 0;i < n;i++) {
//
//        }
//    }

    public void spam(String number, int n) {
        SmsManager s = SmsManager.getDefault();
        for (int i=0; i < n;i++){
            s.sendTextMessage(number, null, "You need to let the system know you are sending a report or not sending a report. Type /noreport for no report or /report for a report.", null, null);
        }
    }
}
