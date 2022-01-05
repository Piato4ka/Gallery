package com.example.gallery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallery.R;
import com.example.gallery.entities.PhotoFavorites;
import com.example.gallery.models.PhotoViewModel;

import java.util.LinkedList;
import java.util.List;


public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.FavViewHolder> {

    private List<PhotoFavorites> photoList = new LinkedList<>();
    private PhotoViewModel photoViewModel;

    public FavListAdapter(PhotoViewModel photoViewModel) {
        this.photoViewModel = photoViewModel;
    }

    public void setPhotoList(List<PhotoFavorites> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public FavListAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_item,
                parent, false);
        return new FavListAdapter.FavViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavListAdapter.FavViewHolder holder, int position) {
        String photoURL = photoList.get(position).getURL();
        Glide.with(holder.picture).load(photoURL).into(holder.picture);
        String textQuery = photoList.get(position).getQueryText();
        holder.searchQuery.setText(textQuery);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView picture;
        private TextView searchQuery;
        private Button delete;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture_favorite);
            searchQuery = itemView.findViewById(R.id.search_query);
            delete = itemView.findViewById(R.id.delete_from_favorite);
            delete.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            photoViewModel.delete(photoList.get(mPosition));
        }
    }
}


