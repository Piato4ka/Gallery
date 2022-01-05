package com.example.gallery;

import androidx.room.ColumnInfo;

public class SearchHistory {
    @ColumnInfo(name = "queryText")
    private String queryText;

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
}
