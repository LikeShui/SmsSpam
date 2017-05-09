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
    private ArrayList<Officer> notSubmitted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Android initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MANAGER = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Make the button operational to spam the shit out of whoever is in notSubmitted.
        //Find it through its id, typecast, and set it's listener
        Button spammer = (Button) findViewById(R.id.spam);
        spammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get spamcount, grab the text. For some dumb reason getText() returns Editable, not string.
                //Make it a string. Parse it to int. This can never fail because the input method is only numbers.
                //Why do we have to do this in the onClick listener? Because if we don't then the value
                //of spamCount is going to be nil since it'll get the value from onCreate().
                try {
                    int n = Integer.parseInt(((EditText) findViewById(R.id.spamCount)).getText().toString());
                    new Spam().spam(notSubmitted, n);
                } catch (NumberFormatException n) {
                    Toast.makeText(v.getContext(), "Must put a number in text box.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Officer.reset();
                notSubmitted = Officer.notSubmitted();
                populateText();
            }
        });
    }

   @Override
    protected void onResume() {
        //onResume is run after onCreate before the activity is live, always.
        //so something we always want to do anyways is update the notSubmitted list.
        //we did that here, and updated the text.
        super.onResume();
        notSubmitted = Officer.notSubmitted();
        populateText();
    }

    private void populateText() {
        //This one is pretty simple to understand.
        TextView t = (TextView) findViewById(R.id.notSubmitted);
        TextView idiotCount = (TextView) findViewById(R.id.idiotCount);
        String s = "";
        int n = this.notSubmitted.size();
        if (n == 0)
            idiotCount.setText("Everyone submitted, flat out fucking amazing.");
        else
            idiotCount.setText("Make these " + n + " idiot(s) submit:");
        for (int i = 0; i < n; i++)
            s += "|"+this.notSubmitted.get(i).getNumber() + "|\n";
        t.setText(s);
    }
}
