package com.example.gallery;

import android.content.Context;
import android.content.SharedPreferences;

public class GalleryPreferences {
    final static String PARAM_LAST_QUERY = "prefs";
    private SharedPreferences mPreferences;

    public GalleryPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PARAM_LAST_QUERY, Context.MODE_PRIVATE);
    }

    public void setParamLastQuery(String query) {
        mPreferences.edit().putString(PARAM_LAST_QUERY, query).apply();
    }

    public String getParamLastQuery() {
        return mPreferences.getString(PARAM_LAST_QUERY, null);
    }

    public boolean containsLastQuery() {
        return mPreferences.contains(PARAM_LAST_QUERY);
    }
}
