package com.example.focustime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.focustime.focus.FocusManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DiaryFragment extends Fragment {
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
        TextView diaryTex = view.findViewById(R.id.diaryText);
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy z");
        diaryTex.setText(formatter.format(date));
        Button returnBtn = view.findViewById(R.id.button);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSwitchToNextFragment(null);
            }
        });
    }
}
