package com.example.focustime.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.focustime.history.History;
import com.example.focustime.history.HistoryDAO;
import com.example.focustime.util.Converters;

@Database(entities = {History.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FocusTimeDatabase extends RoomDatabase {
    public abstract HistoryDAO historyDAO();
    private static FocusTimeDatabase INSTANCE;

    public static FocusTimeDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (FocusTimeDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FocusTimeDatabase.class, "focus_time_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
