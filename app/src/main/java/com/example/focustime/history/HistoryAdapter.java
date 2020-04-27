package com.example.focustime.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focustime.R;
import com.example.focustime.util.Utility;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final LayoutInflater inflater;
    private List<History> historyList;

    public HistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
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
            String textFormat = "%s  %s  %s";
            String focusTime = Utility.formatElapseTime(current.getFocusTime());
            String distractTime = Utility.formatElapseTime(current.getDistractTime());
            String datePattern = "MM/dd";
            String dateValue = new SimpleDateFormat(datePattern).format(current.getFocusDate());
            String textValue = String.format(textFormat, dateValue, focusTime, distractTime);
            holder.historyItemView.setText(textValue);
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
