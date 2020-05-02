package com.example.focustime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.focustime.history.History;
import com.example.focustime.history.HistoryAdapter;
import com.example.focustime.history.HistoryViewModel;
import com.example.focustime.util.Utility;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {
    private static HistoryViewModel historyViewModel;
    static SwitchPageFragmentListener listener;
    static Date currentMonth = Utility.getCurrentMonth();

    public HistoryFragment(SwitchPageFragmentListener listener){
        HistoryFragment.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView historyText = view.findViewById(R.id.historyText);
        Calendar c = Calendar.getInstance();
        c.setTime(currentMonth);
        historyText.setText(String.format("%s\n%s", c.get(Calendar.YEAR), Utility.getMonthWord(c.get(Calendar.MONTH))));

        ImageButton prevBtn = view.findViewById(R.id.prevMonth);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPreviousMonth();
            }
        });
        ImageButton nextBtn = view.findViewById(R.id.nextMonth);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNextMonth();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerHistory);
        final HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), listener);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        historyViewModel.getMonthlyHistories(currentMonth).observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyAdapter.setHistoryList(histories);
            }
        });

    }

    public static void upsertHistory(History history){
        historyViewModel.upsert(history);
    }

    public void toPreviousMonth() {
        try {
            currentMonth = Utility.addMonth(currentMonth, -1);
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void toNextMonth() {
        try {
            currentMonth = Utility.addMonth(currentMonth, 1);
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void toCurrentMonth(){
        currentMonth = Utility.getCurrentMonth();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
