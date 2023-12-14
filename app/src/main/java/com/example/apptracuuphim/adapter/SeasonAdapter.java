package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.SeasonListener;
import com.example.apptracuuphim.model.Film.Image;
import com.example.apptracuuphim.model.Tv.Season;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    private List<Season> seasonList;
    private SeasonListener seasonListener;

    public SeasonAdapter(List<Season> seasonList, SeasonListener seasonListener) {
        this.seasonList = seasonList;
        this.seasonListener = seasonListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout itemSeason;
        public TextView tvSeason;

        public ViewHolder(View view) {
            super(view);
            itemSeason = view.findViewById(R.id.item_season);
            tvSeason = view.findViewById(R.id.tv_season);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_button_season, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Season season = seasonList.get(position);
        if (season == null) {
            return;
        }

        viewHolder.tvSeason.setText(String.valueOf(season.getSeason_number()));
        viewHolder.itemSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seasonListener.onClick(season);
            }
        });
    }


    @Override
    public int getItemCount() {
        return seasonList.size();
    }
}
