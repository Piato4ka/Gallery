package com.example.gallery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallery.OnAdapterItemClickListener;
import com.example.gallery.R;
import com.example.gallery.TouchCallback;
import com.example.gallery.activities.MainActivity;
import com.example.gallery.entities.PhotoFavorites;
import com.example.gallery.models.PhotoViewModel;

import java.util.Collections;
import java.util.LinkedList;


public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> implements TouchCallback {
    private final LinkedList<String> mPhotoList;
    private OnAdapterItemClickListener adapterItemClickListener;
    private PhotoViewModel photoViewModel;

    public void setPhotoViewModel(PhotoViewModel photoViewModel) {
        this.photoViewModel = photoViewModel;
    }

    public PhotoListAdapter(LinkedList<String> photoList, OnAdapterItemClickListener listener) {
        this.mPhotoList = photoList;
        this.adapterItemClickListener = listener;
    }

    @Override
    public void onItemDismiss(int position) {
        mPhotoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mPhotoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mPhotoList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pictureURL;
        private ImageView picture;
        private Button addToFavoritesButton;


        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureURL = itemView.findViewById(R.id.picture_URL);
            picture = itemView.findViewById(R.id.picture);
            addToFavoritesButton = itemView.findViewById(R.id.add_to_favorites_button);
            picture.setOnClickListener(this::onClick);
            addToFavoritesButton.setOnClickListener(this::addToFavorites);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String photoURL = mPhotoList.get(mPosition);
            adapterItemClickListener.onAdapterItemClickListener(photoURL);
        }

        public void addToFavorites(View view) {
            int mPosition = getLayoutPosition();
            String photoURL = mPhotoList.get(mPosition);
            PhotoFavorites photo = new PhotoFavorites(MainActivity.getSearchString(), photoURL);
            photo.setRequester(MainActivity.ACTUAL_USER);

            if (((Button) view).getText().toString().equals("add to favorites")) {
                photoViewModel.insert(photo);
                ((Button) view).setText("delete from favorites");
            } else {
                ((Button) view).setText("add to favorites");
                photoViewModel.delete(photo);
            }
        }
    }


    @NonNull
    @Override
    public PhotoListAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photolist_item,
                parent, false);
        return new PhotoViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListAdapter.PhotoViewHolder holder, int position) {
        String mCurrent = mPhotoList.get(position);
        holder.pictureURL.setText(mCurrent);
        Glide.with(holder.picture).load(mCurrent).into(holder.picture);

    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }


    public static class TouchHelperCallback extends ItemTouchHelper.Callback {

        private final TouchCallback touchCallback;

        public TouchHelperCallback(TouchCallback callback) {
            touchCallback = callback;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            touchCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            touchCallback.onItemDismiss(viewHolder.getAdapterPosition());
        }

    }
}
