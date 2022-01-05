package com.example.gallery.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table", indices = {@Index(value = "userName", unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "userName")
    private String UserName;

    public User(@NonNull String userName) {
        UserName = userName;
    }

    public User() {
    }

    @NonNull
    public String getUserName() {
        return UserName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(@NonNull String userName) {
        UserName = userName;
    }
}
