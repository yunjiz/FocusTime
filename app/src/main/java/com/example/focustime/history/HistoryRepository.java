package com.example.focustime.history;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.focustime.db.FocusTimeDatabase;

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

    public void insert(History history){
        new insertAsyncTask(historyDAO).execute(history);
    }

    private static class insertAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDAO asyncTaskDao;

        insertAsyncTask(HistoryDAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            asyncTaskDao.insert(histories[0]);
            return null;
        }
    }
}


