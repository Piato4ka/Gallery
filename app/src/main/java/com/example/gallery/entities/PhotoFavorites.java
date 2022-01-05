package com.example.gallery.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "photo_table", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userName", childColumns = "requester", onDelete = CASCADE))
public class PhotoFavorites {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "queryText")
    private String queryText;

    @ColumnInfo(name = "URL")
    private String URL;


    @ColumnInfo(name = "requester")
    private String Requester;


    public PhotoFavorites(@NonNull String query, @NonNull String URL) {
        this.queryText = query;
        this.URL = URL;
    }

    public PhotoFavorites() {
    }

    public PhotoFavorites(String queryText) {
        this.queryText = queryText;
    }

    @NonNull
    public String getQueryText() {
        return queryText;
    }

    @NonNull
    public String getURL() {
        return URL;
    }

    @NonNull
    public String getRequester() {
        return Requester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRequester(@NonNull String requester) {
        Requester = requester;
    }

    public void setQueryText(@NonNull String queryText) {
        this.queryText = queryText;
    }

    public void setURL(@NonNull String URL) {
        this.URL = URL;
    }

}
