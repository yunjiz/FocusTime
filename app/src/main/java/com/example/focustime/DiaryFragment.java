package com.example.focustime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.focustime.diary.Diary;
import com.example.focustime.diary.DiaryViewModel;
import com.example.focustime.focus.FocusManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DiaryFragment extends Fragment {
    private static DiaryViewModel diaryViewModel;
    static SwitchPageFragmentListener listener;
    Date date;

    public DiaryFragment(SwitchPageFragmentListener listener, Date date) {
        DiaryFragment.listener = listener;
        this.date = date;
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
        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);

        TextView diaryText = view.findViewById(R.id.diaryText);
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy z");
        diaryText.setText(formatter.format(date));

        Diary diary = diaryViewModel.query(date);
        if(diary == null){
            diary = new Diary();
            diary.setFocusDate(date);
        }
        final EditText editText = view.findViewById(R.id.diaryEdit);
        editText.setText(diary.getContent());

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
}
