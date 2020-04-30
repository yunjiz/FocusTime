package com.example.focustime.diary;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;

@Dao
public interface DiaryDAO {
    @Insert
    void insert(Diary diary);

    @Update
    void update(Diary diary);

    @Query("DELETE FROM diary_table")
    void deleteAll();

    @Query("SELECT * FROM diary_table WHERE focusDate = :date")
    Diary findByDate(Date date);
}
