package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.GenresListener;
import com.example.apptracuuphim.model.Film.Genres;
import com.example.apptracuuphim.model.Film.Image;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {
    private List<Genres> genresList;
    private GenresListener genresListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatButton btnFirmGenres;

        public ViewHolder(View view) {
            super(view);
            btnFirmGenres = view.findViewById(R.id.btn_firm_genres);
        }
    }

    public GenresAdapter(List<Genres> genresList, GenresListener genresListener) {
        this.genresList = genresList;
        this.genresListener = genresListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_btn, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Genres genres = genresList.get(position);
        if (genres == null) {
            return;
        }
        viewHolder.btnFirmGenres.setText(genres.getName());
        viewHolder.btnFirmGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genresListener.onClick(genres);
            }
        });
    }


    @Override
    public int getItemCount() {
        return genresList.size();
    }
}
