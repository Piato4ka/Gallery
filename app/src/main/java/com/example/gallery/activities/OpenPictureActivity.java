package com.example.gallery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.gallery.R;

public class OpenPictureActivity extends AppCompatActivity {
    private TextView searchTextView;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_picture);
        searchTextView = findViewById(R.id.search_string);
        picture = findViewById(R.id.picture_all_screen);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        if (intent.getStringExtra("QUERY").equals("fromMemory")) {
            picture.setImageBitmap(BitmapFactory.decodeFile(url));
        } else {
            String lastQuery = intent.getStringExtra("QUERY");
            Glide.with(this).load(url).into(picture);
            searchTextView.setText(lastQuery);
        }
    }

}