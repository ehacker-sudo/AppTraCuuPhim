package com.example.apptracuuphim.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Movie.Movie;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Film> filmList;
    private String imageType;
    private int mainImageWidth;
    private FilmClickListener filmClickListener;

    public ItemAdapter(Context context, List<Film> filmList, String imageType, FilmClickListener filmClickListener) {
        this.context = context;
        this.filmList = filmList;
        this.imageType = imageType;
        this.filmClickListener = filmClickListener;
    }

    public ItemAdapter(Context context, List<Film> filmList, String imageType, int mainImageWidth, FilmClickListener filmClickListener) {
        this.context = context;
        this.filmList = filmList;
        this.imageType = imageType;
        this.mainImageWidth = mainImageWidth;
        this.filmClickListener = filmClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (this.imageType.equals("banner")){

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_film_banner, parent, false);
            return new BannerViewHolder(view);

        } else if (this.imageType.equals("poster")){

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_film_poster, parent, false);
            return new PosterViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_film_backdrop, parent, false);
            return new BackDropViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = filmList.get(position);
        if (film == null) {
            return;
        }
        if (this.imageType.equals("banner")){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            if (mainImageWidth > 0) {
                bannerViewHolder.ItemFilm.getLayoutParams().width = mainImageWidth;
            }
            if (film.getMedia_type().equals("movie")) {
                bannerViewHolder.TvFilmTitle.setText(film.getTitle());
                if (film.getRelease_date().length() < 4) {
                    bannerViewHolder.TvFilmDate.setText("Null");
                }
                else {
                    bannerViewHolder.TvFilmDate.setText(film.getRelease_date().substring(0,4));
                }
                bannerViewHolder.IvFilmType.setImageResource(R.drawable.banner_film);
                bannerViewHolder.TvFilmType.setText("Movie");
            } else if (film.getMedia_type().equals("tv")) {
                bannerViewHolder.TvFilmTitle.setText(film.getName());
                if (film.getFirst_air_date().length() < 4) {
                    bannerViewHolder.TvFilmDate.setText("Null");
                }
                else {
                    bannerViewHolder.TvFilmDate.setText(film.getFirst_air_date().substring(0,4));
                }
                bannerViewHolder.IvFilmType.setImageResource(R.drawable.banner_tv);
                bannerViewHolder.TvFilmType.setText("Tv Serie");
            }
            bannerViewHolder.TvFilmRate.setText(String.valueOf(film.getVote_average()));

            Glide.with(context).load("https://image.tmdb.org/t/p/original" + film.getBackdrop_path()).into(bannerViewHolder.IvFilmImage);
            bannerViewHolder.ItemFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filmClickListener.onClickItemMovie(film);
                }
            });
        } else if (this.imageType.equals("poster")){
            PosterViewHolder posterViewHolder = (PosterViewHolder) holder;

            if (mainImageWidth > 0) {
                posterViewHolder.IvFilmImage.getLayoutParams().width = mainImageWidth;
            }

            if (film.getMedia_type().equals("movie")) {
                posterViewHolder.TvFilmTitle.setText(film.getTitle());
            } else if (film.getMedia_type().equals("tv")) {
                posterViewHolder.TvFilmTitle.setText(film.getName());
            }
            posterViewHolder.TvFilmRate.setText(Double.toString(film.getVote_average()));
            Glide.with(context).load("https://image.tmdb.org/t/p/original" + film.getPoster_path()).into(posterViewHolder.IvFilmImage);
            posterViewHolder.ItemFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filmClickListener.onClickItemMovie(film);
                }
            });
        } else {
            BackDropViewHolder backDropViewHolder = (BackDropViewHolder) holder;
            if (mainImageWidth > 0) {
                backDropViewHolder.ItemFilm.getLayoutParams().width = mainImageWidth;
            }
            if (film.getMedia_type().equals("movie")) {
                backDropViewHolder.TvFilmTitle.setText(film.getTitle());
                if (film.getRelease_date().length() < 4) {
                    backDropViewHolder.TvFilmDate.setText("Null");
                }
                else {
                    backDropViewHolder.TvFilmDate.setText(film.getRelease_date().substring(0,4));
                }
                backDropViewHolder.IvFilmType.setImageResource(R.drawable.banner_film);
                backDropViewHolder.TvFilmType.setText("Movie");
            } else if (film.getMedia_type().equals("tv")) {
                backDropViewHolder.TvFilmTitle.setText(film.getName());
                if (film.getFirst_air_date().length() < 4) {
                    backDropViewHolder.TvFilmDate.setText("Null");
                }
                else {
                    backDropViewHolder.TvFilmDate.setText(film.getFirst_air_date().substring(0,4));
                }
                backDropViewHolder.IvFilmType.setImageResource(R.drawable.banner_tv);
                backDropViewHolder.TvFilmType.setText("Tv Serie");
            }
            backDropViewHolder.TvFilmRate.setText(String.valueOf(film.getVote_average()));

            Glide.with(context).load("https://image.tmdb.org/t/p/original" + film.getBackdrop_path()).into(backDropViewHolder.IvFilmImage);
            backDropViewHolder.ItemFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filmClickListener.onClickItemMovie(film);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public class BackDropViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ItemFilm;
        public TextView TvFilmDate;
        public ImageView IvFilmType;
        public ImageView IvFilmImage;
        public TextView TvFilmType;
        public TextView TvFilmTitle;
        public TextView TvFilmRate;
        public BackDropViewHolder(View view) {
            super(view);
            TvFilmDate = view.findViewById(R.id.tv_film_date);
            IvFilmType = view.findViewById(R.id.iv_film_type);
            IvFilmImage = view.findViewById(R.id.iv_film_image);
            TvFilmType = view.findViewById(R.id.tv_film_type);
            TvFilmRate = view.findViewById(R.id.tv_film_rate);
            TvFilmTitle = view.findViewById(R.id.tv_film_title);
            ItemFilm = view.findViewById(R.id.item_film);
        }
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ItemFilm;
        public ImageView IvFilmImage;
        public TextView TvFilmTitle;
        public TextView TvFilmRate;

        public PosterViewHolder(View view) {
            super(view);
            IvFilmImage = view.findViewById(R.id.iv_film_image);
            TvFilmRate = view.findViewById(R.id.tv_film_rate);
            TvFilmTitle = view.findViewById(R.id.tv_film_title);
            ItemFilm = view.findViewById(R.id.item_film);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout ItemFilm;
        public TextView TvFilmDate;
        public ImageView IvFilmType;
        public ImageView IvFilmImage;
        public TextView TvFilmType;
        public TextView TvFilmTitle;
        public TextView TvFilmRate;

        public BannerViewHolder(View view) {
            super(view);
            TvFilmDate = view.findViewById(R.id.tv_film_date);
            IvFilmType = view.findViewById(R.id.iv_film_type);
            IvFilmImage = view.findViewById(R.id.iv_film_image);
            TvFilmType = view.findViewById(R.id.tv_film_type);
            TvFilmRate = view.findViewById(R.id.tv_film_rate);
            TvFilmTitle = view.findViewById(R.id.tv_film_title);
            ItemFilm = view.findViewById(R.id.item_film);
        }
    }

}