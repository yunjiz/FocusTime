package com.example.focustime.util;

import android.os.SystemClock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {
    public static String formatElapseTime(long milliseconds){
        long seconds = milliseconds/1000;
        long hours = seconds/3600;
        long minutes = (seconds%3600)/60;
        seconds = seconds%60;
        return String.format("%sh%sm%ss", hours, minutes, seconds);
    }

    public static Date addMonth(Date date, int month) throws ParseException {
        String datePatten = "yyyy/MM/dd";
        SimpleDateFormat df = new SimpleDateFormat(datePatten);
        date = df.parse(df.format(date));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        date = c.getTime();
        return date;
    }

    public static Date getCurrentMonth(){
        Date date = new Date();
        String datePatten = "yyyy/MM";
        SimpleDateFormat df = new SimpleDateFormat(datePatten);
        try {
            date = df.parse(df.format(date));
        } catch (Exception e){
            System.out.println(e);
        }
        return date;
    }

    public static String getMonthWord(int i){
        switch (i){
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }
        return "";
    }
}
