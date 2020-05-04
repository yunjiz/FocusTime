package com.example.focustime;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.focustime.diary.Diary;
import com.example.focustime.diary.DiaryViewModel;
import com.example.focustime.history.History;
import com.example.focustime.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DiaryFragment extends Fragment {
    private static DiaryViewModel diaryViewModel;
    public static SwitchPageFragmentListener listener;
    History history;

    public DiaryFragment(){
        super();
    }

    public static DiaryFragment newInstance(History history){
        Bundle args = new Bundle();
        args.putParcelable("history", history);
        DiaryFragment df = new DiaryFragment();
        df.setArguments(args);
        return df;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.history = getArguments().getParcelable("history");
        //Optional by using getArguments();
        if(savedInstanceState != null && this.history == null){
            this.history = savedInstanceState.getParcelable("history");
        }

        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);

        Date date = history.getFocusDate();

        TextView diaryText = view.findViewById(R.id.diaryText);
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy z");
        diaryText.setText(formatter.format(date));

        TextView diaryTimeText = view.findViewById(R.id.diaryTimeText);
        String validTime = Utility.formatElapseTime(history.getFocusTime() - history.getDistractTime());
        diaryTimeText.setText(validTime);

        Diary diary = diaryViewModel.query(date);
        if(diary == null){
            diary = new Diary();
            diary.setFocusDate(date);
        }
        final EditText editText = view.findViewById(R.id.diaryEdit);
        editText.setText(diary.getContent());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Activity activity = (Activity) getContext();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        editText.setMaxHeight(height/2);

        Button returnBtn = view.findViewById(R.id.button);
        final Diary finalDiary = diary;
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDiary.setContent(editText.getText().toString());
                diaryViewModel.upsert(finalDiary);
                listener.onSwitchToNextFragment(null);
            }
        });
    }

    public void doneEditing(){
        Button returnBtn = getView().findViewById(R.id.button);
        returnBtn.callOnClick();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("history", history);
    }
}
