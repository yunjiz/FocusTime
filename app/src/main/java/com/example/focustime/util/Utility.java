package com.example.focustime.util;

import android.os.SystemClock;

public class Utility {
    public static String formatElapseTime(long milliseconds){
        long seconds = milliseconds/1000;
        long hours = seconds/3600;
        long minutes = (seconds%3600)/60;
        seconds = seconds%60;
        return String.format("%sh%sm%ss", hours, minutes, seconds);
    }
}
