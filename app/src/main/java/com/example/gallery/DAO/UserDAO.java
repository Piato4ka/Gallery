package com.example.gallery.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gallery.entities.User;

import java.util.List;


@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * from user_table")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * from user_table WHERE userName = :username LIMIT 1")
    User getUserName(String username);

}
