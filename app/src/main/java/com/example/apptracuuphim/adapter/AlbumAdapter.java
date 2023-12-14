package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.model.Film.Image;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Context context;
    private List<Image> imageList;
    private LinearLayout.LayoutParams layoutParams;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFilm;

        public ViewHolder(View view) {
            super(view);
            imgFilm = view.findViewById(R.id.img_film);
        }
    }

    public AlbumAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
        this.layoutParams = null;
    }

    public AlbumAdapter(Context context, List<Image> imageList, LinearLayout.LayoutParams layoutParams) {
        this.context = context;
        this.imageList = imageList;
        this.layoutParams = layoutParams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Image image = imageList.get(position);
        if (image == null) {
            return;
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + image.getFile_path()).into(viewHolder.imgFilm);
        if (this.layoutParams != null) {
            viewHolder.itemView.setLayoutParams(this.layoutParams);
        }
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
