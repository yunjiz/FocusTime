package com.example.focustime.history;

import android.graphics.Color;

import com.example.focustime.util.Utility;

import java.text.SimpleDateFormat;

public class HistoryUI {
    private int color;
    private String text;

    final String textFormat = "%s  %s  %s";
    final String datePattern = "MM/dd";


    public HistoryUI(History history) {
        this.text = createText(history);
        this.color = createColor(history, new Setting(4,8,10));
    }

    private String createText(History history){
        String focusTime = Utility.formatElapseTime(history.getFocusTime());
        String distractTime = Utility.formatElapseTime(history.getDistractTime());
        String dateValue = new SimpleDateFormat(datePattern).format(history.getFocusDate());
        return String.format(textFormat, dateValue, focusTime, distractTime);
    }

    private int createColor(History history, Setting setting){
        long validFocus = history.getFocusTime() - history.getDistractTime();
        if(validFocus<setting.getLowBar()){
            return Color.RED;
        } else if(validFocus<setting.getHighBar()){
            return Color.GREEN;
        } else if(validFocus<setting.getUltraBar()){
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

    private class Setting {
        long milliSec = 60*60*1000;

        long lowBarHr = 4;
        long highBarHr = 8;
        long ultraBarHr = 12;

        public Setting(long lowBarHr, long highBarHr, long ultraBarHr) {
            this.lowBarHr = lowBarHr;
            this.highBarHr = highBarHr;
            this.ultraBarHr = ultraBarHr;
        }

        public long getLowBar() {
            return lowBarHr * milliSec;
        }

        public long getHighBar() {
            return highBarHr * milliSec;
        }

        public long getUltraBar() {
            return ultraBarHr * milliSec;
        }
    }
}
