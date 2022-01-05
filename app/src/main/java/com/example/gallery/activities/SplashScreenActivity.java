package com.example.gallery.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private final int DURATION = 3000;
    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(DURATION);
                    } catch (InterruptedException e) {
                    } finally {
                        Intent intent = new Intent(getBaseContext(),
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

        };
        mSplashThread.start();
    }
}