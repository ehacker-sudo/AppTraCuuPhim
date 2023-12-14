package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.listener.MiniItemListener;
import com.example.apptracuuphim.model.Film.ExtraInfo;
import com.example.apptracuuphim.model.Film.Image;
import com.example.apptracuuphim.model.Movie.Movie;

import java.util.HashMap;
import java.util.List;

public class ExtraDetailAdapter extends RecyclerView.Adapter<ExtraDetailAdapter.ViewHolder> {
    private List<ExtraInfo> extraInfoList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView infoTitle;
        public TextView TvInfoDetail;
        public ImageView itemAction;
        public RelativeLayout itemFilmExtra;

        public ViewHolder(View view) {
            super(view);
            infoTitle = view.findViewById(R.id.info_title);
            TvInfoDetail = view.findViewById(R.id.tv_info_detail);
            itemAction = view.findViewById(R.id.item_action);
            itemFilmExtra = view.findViewById(R.id.item_film_extra);
        }
    }

    public ExtraDetailAdapter(List<ExtraInfo> extraInfoList) {
        this.extraInfoList = extraInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_film_extra_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ExtraInfo extraInfo = extraInfoList.get(position);
        if (extraInfo == null) {
            return;
        }
        if (extraInfo.getContent().isEmpty()) {
            return;
        }

        viewHolder.infoTitle.setText(extraInfo.getName());
//        if (extraInfo.getName().length() < 20){
//            viewHolder.infoTitle.setText(extraInfo.getName());
//        } else {
//            String name = "";
//            for (int i = 0; i <= 20; i++) {
//                name = name + extraInfo.getName().charAt(i);
//            }
//            viewHolder.infoTitle.setText(name + "...");
//        }
        viewHolder.TvInfoDetail.setText(extraInfo.getContent());
        if (extraInfo.getMiniItemListener() == null) {
            viewHolder.itemAction.setVisibility(View.GONE);
        }
        else {
            viewHolder.itemFilmExtra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    extraInfo.getMiniItemListener().onclick();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return extraInfoList.size();
    }
}
