package com.example.entrenapp.repository;

import android.util.Log;

import java.time.Duration;

public class TimeParser {

    public static void setOnlySeconds(boolean onlySeconds) {
        TimeParser.reduced = onlySeconds;
    }

    public static boolean getReduced() {
        return reduced;
    }

    private static boolean reduced = false;


    public static String parseSeconds(int seconds) {
        if (reduced) {
            return seconds+"\'\'";
        } else {
            long HH = seconds / 3600;
            long MM = (seconds -(3600*HH))/60;
            long SS = (seconds -(3600*HH)-(MM*60));
            return (HH < 10 ? "0" : "")+HH+":"+(MM < 10 ? "0" : "")+MM+":"+(SS < 10 ? "0" : "")+SS;
        }
    }

    public static String parseMinutes(int minutes) {
        if (reduced) {
            return minutes+"\'";
        } else {
            long HH = minutes / 60;
            long MM = (minutes % 60) ;
            return (HH < 10 ? "0" : "")+HH+":"+(MM < 10 ? "0" : "")+MM+(HH + MM == 0 ? ":05" : ":00");
        }
    }


}
