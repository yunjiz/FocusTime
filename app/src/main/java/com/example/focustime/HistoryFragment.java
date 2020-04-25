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

import com.example.focustime.history.History;
import com.example.focustime.history.HistoryAdapter;
import com.example.focustime.history.HistoryViewModel;

import java.util.List;

public class HistoryFragment extends Fragment {
    private static HistoryViewModel historyViewModel;

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerHistory);
        final HistoryAdapter historyAdapter = new HistoryAdapter(getActivity());
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getAllHistories().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyAdapter.setHistoryList(histories);
            }
        });
    }

    public static void upsertHistory(History history){
        historyViewModel.upsert(history);
    }
}
