package com.example.focustime.history;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "history_table")
public class History {
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
}
