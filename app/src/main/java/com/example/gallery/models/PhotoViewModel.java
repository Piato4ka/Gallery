package com.example.gallery.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gallery.repositories.PhotoRepository;
import com.example.gallery.SearchHistory;
import com.example.gallery.entities.PhotoFavorites;

import java.util.List;

public class PhotoViewModel extends AndroidViewModel {

    private PhotoRepository photoRepository;
    private LiveData<List<PhotoFavorites>> allPhotos;
    private LiveData<List<SearchHistory>> historySearch;

    public PhotoViewModel(Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
        allPhotos = photoRepository.getAllPhotos();
        historySearch = photoRepository.getHistorySearch();
    }

    public LiveData<List<PhotoFavorites>> getAllPhotos() {
        return allPhotos;
    }

    public LiveData<List<SearchHistory>> getHistorySearch() {
        return historySearch;
    }

    public void insert(PhotoFavorites photo) {
        photoRepository.insert(photo);
    }

    public void delete(PhotoFavorites photo) {
        photoRepository.delete(photo);
    }

}
