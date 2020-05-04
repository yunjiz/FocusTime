package com.example.focustime.focus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.focustime.HistoryFragment;
import com.example.focustime.history.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FocusManager implements Parcelable{
    private long focusBeginTime;
    private long focusStopTime;
    private long distractBeginTime;
    private long distractPauseDelta;
    private boolean focusOn;
    private boolean distractOn;
    private boolean distractFirstRun;

    public FocusManager(){
    }

    protected FocusManager(Parcel in) {
        focusBeginTime = in.readLong();
        focusStopTime = in.readLong();
        distractBeginTime = in.readLong();
        distractPauseDelta = in.readLong();
        focusOn = in.readByte() != 0;
        distractOn = in.readByte() != 0;
        distractFirstRun = in.readByte() != 0;
    }

    public long getFocusBeginTime() {
        return focusBeginTime;
    }

    public long getDistractPauseDelta() {
        return distractPauseDelta;
    }

    public void setDistractPauseDelta(long distractPauseDelta) {
        this.distractPauseDelta = distractPauseDelta;
    }

    public static final Creator<FocusManager> CREATOR = new Creator<FocusManager>() {
        @Override
        public FocusManager createFromParcel(Parcel in) {
            return new FocusManager(in);
        }

        @Override
        public FocusManager[] newArray(int size) {
            return new FocusManager[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(focusBeginTime);
        parcel.writeLong(focusStopTime);
        parcel.writeLong(distractBeginTime);
        parcel.writeLong(distractPauseDelta);
        parcel.writeByte((byte) (focusOn ? 1 : 0));
        parcel.writeByte((byte) (distractOn ? 1 : 0));
        parcel.writeByte((byte) (distractFirstRun ? 1 : 0));
    }
}
