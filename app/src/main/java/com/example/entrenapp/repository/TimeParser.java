package com.example.entrenapp.repository;

import java.time.Duration;

public class TimeParser {

    public static void setOnlySeconds(boolean onlySeconds) {
        TimeParser.onlySeconds = onlySeconds;
    }

    private static boolean onlySeconds = false;

    public static String parseSeconds(int seconds) {
        if (onlySeconds) {
            return seconds+"\'\'";
        } else {
            Duration duration = Duration.ofMillis(seconds*1000);
            long HH = seconds / 3600;
            long MM = (seconds % 3600) / 60;
            long SS = seconds % 60;
            return HH+":"+MM+":"+SS;
        }
    }


}
