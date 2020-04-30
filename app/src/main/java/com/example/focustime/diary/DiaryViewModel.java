package com.example.focustime.diary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Date;

public class DiaryViewModel extends AndroidViewModel {
    private DiaryRepository diaryRepository;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        diaryRepository = new DiaryRepository(application);
    }

    public void upsert(Diary diary){
        diaryRepository.upsert(diary);
    }

    public Diary query(Date date){
        return diaryRepository.query(date);
    }
}
