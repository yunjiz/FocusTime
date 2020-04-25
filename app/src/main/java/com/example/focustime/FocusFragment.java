package com.example.focustime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.focustime.history.History;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FocusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FocusFragment extends Fragment {
    private static long focusBegin;
    private static long focusStop;
    private static long distractBegin;
    private static long distractPause;
    private static boolean focusOn;
    private static boolean distractOn;
    private static boolean distractFirstRun;
    private static FloatingActionButton focusButton;
    private static FloatingActionButton distractButton;
    private static FloatingActionButton addTimeButton;
    private static Chronometer focusMeter;
    private static Chronometer distractMeter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_focus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        focusMeter = getView().findViewById(R.id.focusChronometer);
        distractMeter = getView().findViewById(R.id.distractChronometer);
        focusButton = view.findViewById(R.id.focusActionButton);
        focusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusOnClick();
            }
        });

        distractButton = view.findViewById(R.id.distractActionButton);
        distractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distractClick();
            }
        });

        addTimeButton = view.findViewById(R.id.addTimeActionButton);
        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTimeClick();
            }
        });
    }

    private void focusOnClick(){
        if(!focusOn){
            focusBegin = SystemClock.elapsedRealtime();
            focusMeter.setBase(focusBegin);
            distractMeter.setBase(focusBegin);
            focusMeter.start();
            focusButton.setImageResource(R.drawable.ic_stop_black_24dp);
            distractButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            focusOn = true;
            distractFirstRun = true;
        }else {
            focusStop = SystemClock.elapsedRealtime();
            focusMeter.stop();
            distractMeter.stop();
            focusButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            distractButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            focusOn = false;
            distractOn = false;
            distractPause = 0;

            try {
                String pattern = "MM/dd/yyyy";
                DateFormat df = new SimpleDateFormat(pattern);
                Date today = new Date();
                today.
                today = df.parse(df.format(today));
                History h = new History();
                h.setFocusDate(today);
                HistoryFragment.insertHistory(h);
            }catch (Exception e){
                System.out.print(e);
            }
        }
    }

    private void distractClick(){
        if(!focusOn)
            return;
        if(distractFirstRun){
            distractBegin = SystemClock.elapsedRealtime();
            distractFirstRun = false;
        }

        if(!distractOn){
            distractMeter.setBase(SystemClock.elapsedRealtime()+distractPause);
            distractMeter.start();

            distractButton.setImageResource(R.drawable.ic_pause_black_24dp);
            distractOn = true;
        }else {
            distractPause = distractMeter.getBase() - SystemClock.elapsedRealtime();
            distractMeter.stop();

            distractButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            distractOn = false;
        }
    }

    private void addTimeClick(){
        if(!focusOn)
            return;
        long addTime = 1000;
        if(!distractOn){
            if(SystemClock.elapsedRealtime() + distractPause - addTime > focusBegin){
                distractPause -= addTime;
                distractMeter.setBase(SystemClock.elapsedRealtime()+distractPause);
            }
        } else if (distractMeter.getBase() - addTime > focusBegin){
            distractMeter.setBase(distractMeter.getBase() - addTime);
        }
    }
}
