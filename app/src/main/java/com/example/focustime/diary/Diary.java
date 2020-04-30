package com.example.focustime.diary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "diary_table")
public class Diary {
    @PrimaryKey
    @ColumnInfo(name = "focusDate")
    Date focusDate;
    @ColumnInfo(name = "content")
    String content;

    public Date getFocusDate() {
        return focusDate;
    }

    public void setFocusDate(Date focusDate) {
        this.focusDate = focusDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
