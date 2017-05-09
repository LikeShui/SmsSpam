package com.ouaspel.anthony.smsfiltering;

import java.util.ArrayList;

/**
 * Created by alna173017 on 5/9/2017.
 */
public class Officer {
    private static final String[] phoneNumbers = PhoneNumbers.phoneNumbers;
    public static int LENGTH = phoneNumbers.length;
    private final String number;
    public boolean report = false;
    public boolean responded = false;
    public static Officer[] officerList = Officer.retrieveAllOfficers();

    private Officer(String number) {
        this.number = number;
        this.report = false;
        this.responded = false;
    }

    public boolean numberIs(String s) {
        if (this.number.equals(s))
            return true;
        return false;
    }

    public String getNumber() {
        return this.number;
    }

    private static Officer[] retrieveAllOfficers() {
        Officer[] x = new Officer[LENGTH];
        for(int i=0;i<LENGTH;i++) {
            x[i] = new Officer(phoneNumbers[i]);
        }
        return x;
    }

    public static ArrayList<Officer> notSubmitted() {
        ArrayList<Officer> a = new ArrayList<Officer>();
        for (Officer o : officerList) {
            if (!o.responded)
                a.add(o);
        }
        return a;
    }

    public static void reset() {
        for(Officer o : officerList) {
            o.responded = false;
            o.report = false;
        }
    }
}
