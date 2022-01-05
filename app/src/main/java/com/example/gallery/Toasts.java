package com.example.gallery;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.StringRes;

public class Toasts {

    public static void showShort(final Context context, @StringRes final int messageRes) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show();
    }
}
