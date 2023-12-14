package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.VideoListener;
import com.example.apptracuuphim.model.Film.Video;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private Context context;
    private List<Video> videoList;
    private VideoListener videoListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout itemVideo;
        public TextView videoType;
        public TextView videoRuntime;
        public TextView videoName;
        public ImageView imageView;
        public CountDownTimer timer;

        public ViewHolder(View view) {
            super(view);
            itemVideo = view.findViewById(R.id.item_video);
            videoType = view.findViewById(R.id.video_type);
            videoRuntime = view.findViewById(R.id.video_runtime);
            videoName = view.findViewById(R.id.video_name);
            imageView = view.findViewById(R.id.video_image_view);
        }
    }

    public VideoAdapter(Context context, List<Video> videoList, VideoListener videoListener) {
        this.context = context;
        this.videoList = videoList;
        this.videoListener = videoListener;
    }

    public void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Video video = videoList.get(position);
        if (video == null) {
            return;
        }

        Glide.with(context).load("https://img.youtube.com/vi/" + video.getKey() + "/1.jpg").into(viewHolder.imageView);
        viewHolder.videoType.setText(video.getType());
        viewHolder.videoName.setText(video.getName());
        viewHolder.videoRuntime.setText("");
        viewHolder.itemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoListener.onClick(video);
            }
        });
    }

}
