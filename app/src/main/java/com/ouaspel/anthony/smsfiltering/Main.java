package com.ouaspel.anthony.smsfiltering;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main extends Activity {
    public static TelephonyManager MANAGER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Android initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MANAGER = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Make the button operational to spam the shit out of whoever is in notSubmitted.
        //Find it through its id, typecast, and set it's listener
        Button reset = (Button) findViewById(R.id.button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = validateStringInput();
                if (s != null) {
                    int spamCount = validateIntInput();
                    if (spamCount > 0 && spamCount < 151)
                        new Spam().spam(s, spamCount);
                    else
                        Toast.makeText(Main.this, "Your number makes no sense, or just so big it'll crash the phone.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int validateIntInput() {
        int x = -1;
        try {
            x = Integer.parseInt(((EditText) findViewById(R.id.spamCount)).getText().toString());
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Some sort of dumb error parsing the int. Some stupid-ass java shit.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Some sort of error, but not by parsing the int. Something wrong in findView, getText, or toString. Or reference error with R.id.", Toast.LENGTH_SHORT).show();
        }
        return x;
    }

    private String validateStringInput() {
        String s = null;
        try {
            s = findViewById(R.id.messageText).toString();
            if (s.length() > 160) {
                Toast.makeText(this, "Message too long. Keep it simpler.", Toast.LENGTH_SHORT).show();
                s = null;
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Something else is wrong with your message.", Toast.LENGTH_SHORT).show();
        } finally {
            return s;
        }
    }
}
