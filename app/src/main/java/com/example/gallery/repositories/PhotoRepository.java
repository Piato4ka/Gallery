package com.example.gallery.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.gallery.DAO.PhotosDAO;
import com.example.gallery.GalleryRoomDatabase;
import com.example.gallery.SearchHistory;
import com.example.gallery.activities.MainActivity;
import com.example.gallery.entities.PhotoFavorites;

import java.util.List;

public class PhotoRepository {

    private PhotosDAO photosDAO;
    private LiveData<List<PhotoFavorites>> allPhotos;
    private LiveData<List<SearchHistory>> historySearch;

    public PhotoRepository(Application application) {
        GalleryRoomDatabase db = GalleryRoomDatabase.getDatabase(application);
        photosDAO = db.photosDAO();
        allPhotos = photosDAO.getAllPhotos(MainActivity.ACTUAL_USER);
        historySearch = photosDAO.getHistorySearch(MainActivity.ACTUAL_USER);
    }

    public LiveData<List<PhotoFavorites>> getAllPhotos() {
        return allPhotos;
    }

    public void insert(PhotoFavorites photo) {
        new AsyncTaskInsert(photosDAO).execute(photo);
    }

    public void delete(PhotoFavorites photo) {
        new AsyncTaskDelete(photosDAO).execute(photo);
    }

    public LiveData<List<SearchHistory>> getHistorySearch() {
        return historySearch;
    }

    private static class AsyncTaskInsert extends AsyncTask<PhotoFavorites, Void, Void> {
        private PhotosDAO mAsyncTaskDao;

        AsyncTaskInsert(PhotosDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PhotoFavorites... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class AsyncTaskDelete extends AsyncTask<PhotoFavorites, Void, Void> {
        private PhotosDAO mAsyncTaskDao;

        AsyncTaskDelete(PhotosDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PhotoFavorites... params) {
            mAsyncTaskDao.deletePhoto(params[0]);
            return null;
        }
    }
}
