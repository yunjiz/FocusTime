package com.example.focustime.history;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focustime.R;
import com.example.focustime.SwitchPageFragmentListener;
import com.example.focustime.util.Utility;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final LayoutInflater inflater;
    private List<History> historyList;
    static SwitchPageFragmentListener listener;

    public HistoryAdapter(Context context, SwitchPageFragmentListener listener) {
        inflater = LayoutInflater.from(context);
        HistoryAdapter.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_history, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if(historyList != null){
            History current = historyList.get(position);
            HistoryUI ui = new HistoryUI(current);
            holder.historyItemView.setText(ui.getText());
            holder.historyItemView.setBackgroundColor(ui.getColor());
            holder.historyItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSwitchToNextFragment();
                }
            });
        } else {
            holder.historyItemView.setText("No data");
        }
    }

    public void setHistoryList(List<History> historyList){
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(historyList!=null){
            return historyList.size();
        }
        return 0;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        private final TextView historyItemView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.historyItemView = itemView.findViewById(R.id.textView);
        }
    }


}
