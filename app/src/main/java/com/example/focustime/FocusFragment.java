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

import com.example.focustime.focus.FocusManager;
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
    private static FocusManager fm;
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
        fm = new FocusManager();

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
        if(!fm.isFocusOn()){
            long currentTime = SystemClock.elapsedRealtime();
            fm.beginFocus(currentTime);

            focusMeter.setBase(currentTime);
            distractMeter.setBase(currentTime);
            focusMeter.start();
            focusButton.setImageResource(R.drawable.ic_stop_black_24dp);
            distractButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }else {
            long currentTime = SystemClock.elapsedRealtime();
            fm.stopFocus(currentTime, distractMeter.getBase());
            focusMeter.stop();
            distractMeter.stop();
            focusButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            distractButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    private void distractClick(){
        if(!fm.isFocusOn())
            return;

        long currentTime = SystemClock.elapsedRealtime();
        if(fm.isDistractFirstRun()){
            fm.distractFirstRun(currentTime);
        }

        if(!fm.isDistractOn()){
            long distractBase = fm.startDistract(currentTime);

            distractMeter.setBase(distractBase);
            distractMeter.start();
            distractButton.setImageResource(R.drawable.ic_pause_black_24dp);
        }else {
            fm.pauseDistract(distractMeter.getBase() - currentTime);

            distractMeter.stop();
            distractButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    private void addTimeClick(){
        long currentTime = SystemClock.elapsedRealtime();
        long distractBaseTime = distractMeter.getBase();
        long addTime = 1000*60;
        long newBaseTime = fm.addDistractTime(addTime, currentTime, distractBaseTime);
        if(newBaseTime!=-1){
            distractMeter.setBase(newBaseTime);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(fm.isFocusOn()){
            focusOnClick();
        }
    }

    public void resetTimer(){
        if(fm.isFocusOn())
            return;

        focusMeter.setBase(SystemClock.elapsedRealtime());
        distractMeter.setBase(SystemClock.elapsedRealtime());
    }
}
