package com.example.focustime.history;

import android.graphics.Color;

import com.example.focustime.util.Utility;

import java.text.SimpleDateFormat;

public class HistoryUI {
    private int color;
    private String text;

    final String textFormat = "%s  %s  %s";
    final String datePattern = "MM/dd";
    final long lowBar = 4*60*60*1000;
    final long highBar = 8*60*60*1000;
    final long ultraBar = 12*60*60*1000;

    public HistoryUI(History history) {
        this.text = createText(history);
        this.color = createColor(history);
    }

    private String createText(History history){
        String focusTime = Utility.formatElapseTime(history.getFocusTime());
        String distractTime = Utility.formatElapseTime(history.getDistractTime());
        String dateValue = new SimpleDateFormat(datePattern).format(history.getFocusDate());
        return String.format(textFormat, dateValue, focusTime, distractTime);
    }

    private int createColor(History history){
        long validFocus = history.getFocusTime() - history.getDistractTime();
        if(validFocus<lowBar){
            return Color.RED;
        } else if(validFocus<highBar){
            return Color.GREEN;
        } else if(validFocus<ultraBar){
            return Color.BLUE;
        } else {
            return Color.MAGENTA;
        }
    }

    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }
}
