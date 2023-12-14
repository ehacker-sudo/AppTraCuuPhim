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
import com.example.apptracuuphim.listener.MiniItemListener;
import com.example.apptracuuphim.listener.NetworkListener;
import com.example.apptracuuphim.listener.SocialMediaListener;
import com.example.apptracuuphim.model.Network.Network;
import com.example.apptracuuphim.model.SocialMedia.Media;

import java.util.List;

public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.ViewHolder> {
    private Context context;
    private List<Media> mediaList;
    private SocialMediaListener socialMediaListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout itemMedia;
        private ImageView ivMedia;
        private TextView name;
        public ViewHolder(View view) {
            super(view);
            ivMedia = view.findViewById(R.id.iv_media);
            name = view.findViewById(R.id.name);
            itemMedia = view.findViewById(R.id.item_media);
        }
    }

    public SocialMediaAdapter(Context context, List<Media> mediaList, SocialMediaListener socialMediaListener) {
        this.context = context;
        this.mediaList = mediaList;
        this.socialMediaListener = socialMediaListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_social_media, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Media media = mediaList.get(position);
        if (media == null) {
            return;
        }
        viewHolder.ivMedia.setImageResource(media.getLogo());
        viewHolder.name.setText(media.getName());
        viewHolder.itemMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialMediaListener.onclick(media);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
