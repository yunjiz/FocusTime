package com.example.focustime.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert
    void insert(History history);

    @Update
    void update(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();

    @Query("SELECT * FROM history_table WHERE focusDate = :date")
    History findByDate(Date date);

    @Query("SELECT * FROM history_table ORDER BY focusDate desc")
    LiveData<List<History>> getAllHistories();
}
