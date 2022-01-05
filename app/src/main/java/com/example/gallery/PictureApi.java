package com.example.gallery;

import com.example.gallery.models.PhotosModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PictureApi {

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<PhotosModel> getPicturesLinks(
            @Query("api_key") String apiKey,
            @Query("text") String text
    );

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<PhotosModel> getPicturesByLocation(
            @Query("api_key") String apiKey,
            @Query("lat") String lat,
            @Query("lon") String lon
    );
}
