package com.example.gallery.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gallery.adapters.HistoryListAdapter;
import com.example.gallery.R;
import com.example.gallery.SearchHistory;
import com.example.gallery.models.PhotoViewModel;

import java.util.List;

public class SearchHistoryActivity extends AppCompatActivity {

    private PhotoViewModel photoViewModel;
    private RecyclerView mRecyclerView;
    private HistoryListAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        mRecyclerView = findViewById(R.id.history_recyclerview);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        historyListAdapter = new HistoryListAdapter();
        mRecyclerView.setAdapter(historyListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchHistoryActivity.this));

        photoViewModel.getHistorySearch().observe(this, new Observer<List<SearchHistory>>() {
            @Override
            public void onChanged(@Nullable final List<SearchHistory> history) {
                historyListAdapter.setSearchHistory(history);
                historyListAdapter.notifyDataSetChanged();
            }
        });
    }
}