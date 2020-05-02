package com.example.focustime.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository historyRepository;
    private LiveData<List<History>> allHistories;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyRepository = new HistoryRepository(application);
        allHistories = historyRepository.getAllHistories();
    }

    public LiveData<List<History>> getAllHistories() {return allHistories;}

    public LiveData<List<History>> getMonthlyHistories(Date date) {
        return historyRepository.getMonthlyHistories(date);
    }

    public void upsert(History history) {
        historyRepository.upsert(history);
    }
}
