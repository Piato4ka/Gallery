package com.example.gallery;

public interface TouchCallback {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
