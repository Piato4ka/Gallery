package com.example.gallery.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.gallery.SearchHistory;
import com.example.gallery.entities.PhotoFavorites;

import java.util.List;


@Dao
public interface PhotosDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PhotoFavorites photo);

    @Delete
    void deletePhoto(PhotoFavorites photo);

    @Query("SELECT * from photo_table WHERE URL IS NOT NULL AND requester=:userName ")
    LiveData<List<PhotoFavorites>> getAllPhotos(String userName);

    @Query("SELECT queryText from photo_table WHERE URL IS NULL AND requester=:userName")
    LiveData<List<SearchHistory>> getHistorySearch(String userName);

}
