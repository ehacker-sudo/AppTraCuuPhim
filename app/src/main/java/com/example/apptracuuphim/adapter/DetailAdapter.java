package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.model.Film.Image;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private Context context;
    private List<Image> imageList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFilm;

        public ViewHolder(View view) {
            super(view);
            imgFilm = view.findViewById(R.id.img_film);
        }
    }

    public DetailAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
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
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
