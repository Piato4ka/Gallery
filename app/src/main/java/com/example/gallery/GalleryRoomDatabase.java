package com.example.gallery;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gallery.DAO.PhotosDAO;
import com.example.gallery.DAO.UserDAO;
import com.example.gallery.entities.PhotoFavorites;
import com.example.gallery.entities.User;


@Database(entities = {User.class, PhotoFavorites.class}, version = 10, exportSchema = false)
public abstract class GalleryRoomDatabase extends RoomDatabase {

    public abstract PhotosDAO photosDAO();

    public abstract UserDAO userDAO();

    private static GalleryRoomDatabase INSTANCE;

    public static GalleryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GalleryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GalleryRoomDatabase.class, "gallery_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
