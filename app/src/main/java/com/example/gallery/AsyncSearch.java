package com.example.gallery;


import android.os.AsyncTask;

import com.example.gallery.models.Photo;
import com.example.gallery.models.PhotosModel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AsyncSearch extends AsyncTask<Void, Void, LinkedList<String>> {
    protected PhotosModel photosModel;
    protected List<Photo> photoList;
    protected final static String BASE_URL = "https://www.flickr.com";
    protected final static String PARAM_API_KEY = "60585179bc88aa401500d140bb11c498";
    protected final LinkedList<String> linksList = new LinkedList<>();
    protected PictureApi pictureApi;

    protected LinkedList<String> doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pictureApi = retrofit.create(PictureApi.class);
        Call<PhotosModel> picturesList = makeCallToServer();

        try {
            Response<PhotosModel> response = picturesList.execute();
            photosModel = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        photoList = photosModel.getPhotos().getPhoto();

        for (int i = 0; i < photoList.size(); i++) {

            linksList.add(photoList.get(i).getLinkFlickrPhotosSearch());
        }
        return linksList;

    }

    public abstract Call<PhotosModel> makeCallToServer();

    protected void onPostExecute(LinkedList<String> list) {
    }
}