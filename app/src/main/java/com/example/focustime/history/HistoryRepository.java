package com.example.focustime.history;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.focustime.db.FocusTimeDatabase;
import com.example.focustime.util.Utility;

import java.util.Date;
import java.util.List;

public class HistoryRepository {
    private HistoryDAO historyDAO;
    private LiveData<List<History>> allHistories;

    HistoryRepository(Application application){
        FocusTimeDatabase db = FocusTimeDatabase.getDatabase(application);
        historyDAO = db.historyDAO();
        allHistories = historyDAO.getAllHistories();
    }

    LiveData<List<History>> getAllHistories() {
        return allHistories;
    }

    public void upsert(History history){
        new upsertAsyncTask(historyDAO).execute(history);
    }

    public LiveData<List<History>> getMonthlyHistories(Date date){
        LiveData<List<History>> monthlyHistories = new LiveData<List<History>>() {
        };
        try {
            monthlyHistories = new queryMonthlyHistoriesTask(historyDAO).execute(date).get();
        } catch (Exception e){
            System.out.println(e);
        }
        return monthlyHistories;
    }

    private static class upsertAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDAO asyncTaskDao;

        upsertAsyncTask(HistoryDAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            History newHistory = histories[0];
            History exsitHistory = asyncTaskDao.findByDate(newHistory.getFocusDate());
            if(exsitHistory != null){
                exsitHistory.setFocusTime(exsitHistory.getFocusTime()+newHistory.getFocusTime());
                exsitHistory.setDistractTime(exsitHistory.getDistractTime()+newHistory.getDistractTime());
                asyncTaskDao.update(exsitHistory);
            }else {
                asyncTaskDao.insert(histories[0]);
            }
            return null;
        }
    }

    private static class queryMonthlyHistoriesTask extends AsyncTask<Date, Void, LiveData<List<History>>>{
        private HistoryDAO asyncTaskDao;
        queryMonthlyHistoriesTask(HistoryDAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<History>> doInBackground(Date... dates) {
            try {
                Date thisMonth = dates[0];
                Date nextMonth = Utility.addMonth(thisMonth, 1);
                return asyncTaskDao.getMonthlyHistories(thisMonth, nextMonth);
            } catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
    }
}


