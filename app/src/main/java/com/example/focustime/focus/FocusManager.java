package com.example.focustime.focus;

import com.example.focustime.HistoryFragment;
import com.example.focustime.history.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FocusManager {
    private static long focusBeginTime;
    private static long focusStopTime;
    private static long distractBeginTime;
    private static long distractPauseDelta;
    private static boolean focusOn;
    private static boolean distractOn;
    private static boolean distractFirstRun;

    public boolean isFocusOn(){
        return focusOn;
    }

    public boolean isDistractOn(){
        return distractOn;
    }

    public boolean isDistractFirstRun(){
        return distractFirstRun;
    }

    public void beginFocus(long currentTime){
        focusBeginTime = currentTime;
        focusOn = true;
        distractFirstRun = true;
        distractOn = false;
    }

    public void stopFocus(long currentTime, long distractBase){
        focusStopTime = currentTime;
        focusOn = false;
        if(distractOn){
            distractOn = false;
            distractPauseDelta = distractBase - currentTime;
        }
        saveHistory();
        distractPauseDelta = 0;
    }

    public void distractFirstRun(long currentTime){
        distractBeginTime = currentTime;
        distractFirstRun = false;
    }

    public long startDistract(long currentTime){
        distractOn = true;
        return currentTime + distractPauseDelta;
    }

    public void pauseDistract(long deltaTime){
        distractOn = false;
        distractPauseDelta = deltaTime;
    }

    public long addDistractTime(long addTime, long currentTime, long distractBaseTime){
        if(!focusOn){
            return -1;
        }
        if(!distractOn){
            if(currentTime + distractPauseDelta - addTime > focusBeginTime){
                distractPauseDelta -= addTime;
                return currentTime+distractPauseDelta;
            }
        } else if(distractBaseTime - addTime > focusBeginTime){
            return distractBaseTime - addTime;
        }
        return -1;
    }

    private void saveHistory(){
        History currHistory = createHistory();
        if(currHistory!=null){
            HistoryFragment.upsertHistory(currHistory);
        }
    }

    private History createHistory(){
        History h = null;
        try {
            String pattern = "MM/dd/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = new Date();
            today = df.parse(df.format(today));
            h = new History();
            h.setFocusDate(today);
            h.setFocusTime(focusStopTime - focusBeginTime);
            h.setDistractTime(-distractPauseDelta);
        } catch (Exception e){
            System.out.println(e);
        }
        return h;
    }
}
