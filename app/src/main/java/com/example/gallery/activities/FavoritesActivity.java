package com.example.gallery.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gallery.adapters.FavListAdapter;
import com.example.gallery.R;
import com.example.gallery.entities.PhotoFavorites;
import com.example.gallery.models.PhotoViewModel;

import java.util.LinkedList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private PhotoViewModel mPhotoViewModel;
    private RecyclerView mRecyclerView;
    private FavListAdapter favListAdapter;
    private List<PhotoFavorites> photos = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mRecyclerView = findViewById(R.id.recyclerview_fav);

        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        favListAdapter = new FavListAdapter(mPhotoViewModel);
        mRecyclerView.setAdapter(favListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));


        mPhotoViewModel.getAllPhotos().observe(this, new Observer<List<PhotoFavorites>>() {
            @Override
            public void onChanged(@Nullable final List<PhotoFavorites> photos) {
                favListAdapter.setPhotoList(photos);
                favListAdapter.notifyDataSetChanged();
            }
        });

    }

}



