package com.example.focustime.diary;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.focustime.db.FocusTimeDatabase;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class DiaryRepository {
    private DiaryDAO diaryDAO;

    public DiaryRepository(Application application){
        FocusTimeDatabase db = FocusTimeDatabase.getDatabase(application);
        diaryDAO = db.diaryDAO();
    }

    public void upsert(Diary diary){
        new upsertAsyncTask(diaryDAO).execute(diary);
    }

    public Diary query(Date date) {
        Diary diary = null;
        try {
            diary = new queryAsyncTask(diaryDAO).execute(date).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return diary;
    }

    private static class queryAsyncTask extends AsyncTask<Date, Void, Diary> {
        private DiaryDAO asyncTaskDao;

        public queryAsyncTask(DiaryDAO asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Diary doInBackground(Date... dates) {
            Date date = dates[0];
            Diary diary = asyncTaskDao.findByDate(date);
            return diary;
        }
    }

    private static class upsertAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDAO asyncTaskDao;

        upsertAsyncTask(DiaryDAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Diary... diaries) {
            Diary newDiary = diaries[0];
            Diary exsitDiary = asyncTaskDao.findByDate(newDiary.getFocusDate());
            if(exsitDiary != null){
                asyncTaskDao.update(newDiary);
            }else {
                asyncTaskDao.insert(newDiary);
            }
            return null;
        }
    }
}
