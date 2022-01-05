package com.example.gallery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;
import com.example.gallery.SearchHistory;

import java.util.LinkedList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HisViewHolder> {

    private List<SearchHistory> searchHistory = new LinkedList<>();

    public void setSearchHistory(List<SearchHistory> searchHistory) {
        this.searchHistory = searchHistory;
    }

    @NonNull
    @Override
    public HistoryListAdapter.HisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.historylist_item,
                parent, false);
        return new HistoryListAdapter.HisViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HisViewHolder holder, int position) {
        String query = searchHistory.get(position).getQueryText();
        holder.searchQuery.setText(query);
    }

    @Override
    public int getItemCount() {
        return searchHistory.size();
    }


    public class HisViewHolder extends RecyclerView.ViewHolder {

        private TextView searchQuery;

        public HisViewHolder(@NonNull View itemView) {
            super(itemView);
            searchQuery = itemView.findViewById(R.id.search_history);
        }
    }
}
