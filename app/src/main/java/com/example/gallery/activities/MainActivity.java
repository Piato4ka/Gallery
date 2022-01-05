package com.example.gallery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import com.example.gallery.AsyncSearch;
import com.example.gallery.AuthorizationHelper;
import com.example.gallery.GalleryPreferences;
import com.example.gallery.OnAdapterItemClickListener;
import com.example.gallery.adapters.PhotoListAdapter;
import com.example.gallery.R;
import com.example.gallery.entities.PhotoFavorites;
import com.example.gallery.models.PhotoViewModel;
import com.example.gallery.models.PhotosModel;
import com.facebook.stetho.Stetho;

import java.io.File;
import java.util.LinkedList;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity implements OnAdapterItemClickListener {
    private EditText searchText;
    private static String searchString;
    private StartAsyncSearch asyncSearch;
    private RecyclerView mRecyclerView;
    private GalleryPreferences galleryPreferences;
    private PhotoViewModel photoViewModel;
    private static final String URL_MESSAGE = "URL";
    private static final String QUERY_MESSAGE = "QUERY";
    public static final String ACTUAL_USER = AuthorizationHelper.getUserName();
    private LinkedList<String> photoList = new LinkedList<String>();
    private PhotoListAdapter mAdapter;
    private String latitude;
    private String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        searchText = findViewById(R.id.search_text);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        galleryPreferences = new GalleryPreferences(this);

        if (galleryPreferences.containsLastQuery())
            searchText.setText(galleryPreferences.getParamLastQuery());

        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

        mAdapter = new PhotoListAdapter(photoList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setPhotoViewModel(photoViewModel);

        ItemTouchHelper.Callback callback =
                new PhotoListAdapter.TouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("latitude")) {
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");
            searchString = new StringBuilder().append(latitude).append(" ").append(longitude).toString();
            searchText.setText(searchString);
            saveHistory();
            StartAsyncSearchLocation asyncSearchLoc = (StartAsyncSearchLocation) new StartAsyncSearchLocation().execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        galleryPreferences.setParamLastQuery(searchString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncSearch != null) asyncSearch.cancel(true);
    }

    public static String getSearchString() {
        return searchString;
    }

    public void startSearch(View view) {
        searchString = searchText.getText().toString();
        saveHistory();
        asyncSearch = (StartAsyncSearch) new StartAsyncSearch().execute();
    }

    @Override
    public void onAdapterItemClickListener(String element) {
        Intent intent = new Intent(this, OpenPictureActivity.class);
        intent.putExtra(URL_MESSAGE, element);
        intent.putExtra(QUERY_MESSAGE, searchString);
        this.startActivity(intent);
    }


    public void goToFavActivity(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);

    }

    public void goToHistoryActivity(View view) {
        Intent intent = new Intent(this, SearchHistoryActivity.class);
        startActivity(intent);
    }

    public void goToCameraActivity(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, 0);
    }

    private void listUpdate(LinkedList<String> list) {
        photoList.clear();
        photoList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    public void goToMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void saveHistory() {
        PhotoFavorites history = new PhotoFavorites(searchString);
        history.setRequester(MainActivity.ACTUAL_USER);
        photoViewModel.insert(history);
    }

    public void goToMyPhotos(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }


    private class StartAsyncSearch extends AsyncSearch {

        @Override
        public Call<PhotosModel> makeCallToServer() {
            return pictureApi.getPicturesLinks(PARAM_API_KEY, searchString);
        }

        @Override
        protected void onPostExecute(LinkedList<String> list) {
            listUpdate(list);
        }
    }


    private class StartAsyncSearchLocation extends AsyncSearch {
        public Call<PhotosModel> makeCallToServer() {
            return pictureApi.getPicturesByLocation(PARAM_API_KEY, latitude, longitude);
        }

        protected void onPostExecute(LinkedList<String> list) {
            listUpdate(list);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);

                    Intent intent = new Intent(this, OpenPictureActivity.class);
                    intent.putExtra(URL_MESSAGE, picturePath);
                    intent.putExtra(QUERY_MESSAGE, "fromMemory");
                    this.startActivity(intent);
                    cursor.close();
                }
            }
        }
    }
}
