package com.example.focustime.history;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "history_table")
public class History implements Parcelable {
    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>(){
        @Override
        public History createFromParcel(Parcel parcel) {
            History h = new History();
            h.setFocusDate(new Date(parcel.readLong()));
            h.setFocusTime(parcel.readLong());
            h.setDistractTime(parcel.readLong());
            return h;
        }

        @Override
        public History[] newArray(int i) {
            return new History[i];
        }
    };

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "focusDate")
    private Date focusDate;
    @ColumnInfo(name = "focusTime")
    private long focusTime;
    @ColumnInfo(name = "distractTime")
    private long distractTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFocusDate() {
        return focusDate;
    }

    public void setFocusDate(Date focusDate) {
        this.focusDate = focusDate;
    }

    public long getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(long focusTime) {
        this.focusTime = focusTime;
    }

    public long getDistractTime() {
        return distractTime;
    }

    public void setDistractTime(long distractTime) {
        this.distractTime = distractTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.focusDate.getTime());
        parcel.writeLong(this.focusTime);
        parcel.writeLong(this.distractTime);
    }
}
