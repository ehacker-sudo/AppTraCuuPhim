package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.EpisodeListener;
import com.example.apptracuuphim.model.Film.Episode;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder>{
    private Context context;
    private List<Episode> episodeList;
    private EpisodeListener episodeListener;

    public EpisodeAdapter(Context context, List<Episode> episodeList, EpisodeListener episodeListener) {
        this.context = context;
        this.episodeList = episodeList;
        this.episodeListener = episodeListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout itemEpisode;
        public ImageView imgEp;
        public TextView tvNameEp;
        public TextView tvEpRate;
        public TextView epRuntime;
        public TextView epDescription;

        public ViewHolder(View view) {
            super(view);
            itemEpisode = view.findViewById(R.id.item_episode);
            imgEp = view.findViewById(R.id.img_ep);
            tvNameEp = view.findViewById(R.id.tv_name_ep);
            tvEpRate = view.findViewById(R.id.tv_ep_rate);
            epRuntime = view.findViewById(R.id.ep_runtime);
            epDescription = view.findViewById(R.id.ep_description);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_episode, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Episode episode = episodeList.get(position);
        if (episode == null) {
            return;
        }
        viewHolder.tvNameEp.setText(episode.getEpisode_number() + "," + episode.getName());
        viewHolder.tvEpRate.setText(Double.toString(episode.getVote_average()));
        viewHolder.epRuntime.setText(episode.getRuntime()+"m");
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + episode.getStill_path()).into(viewHolder.imgEp);
        viewHolder.epDescription.setText(episode.getOverview());
        viewHolder.itemEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                episodeListener.OnClickListener(episode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }
}
