package com.example.focustime.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.focustime.history.History;
import com.example.focustime.history.HistoryDAO;
import com.example.focustime.util.Converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final HistoryDAO dao;
        List<History> historyList;

        PopulateDbAsync(FocusTimeDatabase db){
            dao = db.historyDAO();
            historyList = new ArrayList<>();

            try {
                History h1 = new History();
                String date1 = "2020/4/23";
                Date d1 = new SimpleDateFormat("yyyy/MM/dd").parse(date1);
                h1.setFocusDate(d1);
                h1.setFocusTime(10*3600*1000);
                h1.setDistractTime(1*3600*1000);
                historyList.add(h1);

                History h2 = new History();
                String date2 = "2020/4/20";
                Date d2 = new SimpleDateFormat("yyyy/MM/dd").parse(date2);
                h2.setFocusDate(d2);
                h2.setFocusTime(6*3600*1000);
                h2.setDistractTime(1*3600*1000);
                historyList.add(h2);

                History h3 = new History();
                String date3 = "2020/4/27";
                Date d3 = new SimpleDateFormat("yyyy/MM/dd").parse(date3);
                h3.setFocusDate(d3);
                h3.setFocusTime(2*3600*1000);
                historyList.add(h3);

                History h4 = new History();
                String date4 = "2020/4/21";
                Date d4 = new SimpleDateFormat("yyyy/MM/dd").parse(date4);
                h4.setFocusDate(d4);
                h4.setFocusTime(15*3600*1000);
                historyList.add(h4);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean debug = false;

            if(debug){
                dao.deleteAll();

                for(int i = 0; i<historyList.size(); i++){
                    History history = historyList.get(i);
                    dao.insert(history);
                }
            }
            return null;
        }
    }
}
