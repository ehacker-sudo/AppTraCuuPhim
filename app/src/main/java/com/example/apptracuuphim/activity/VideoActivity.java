package com.example.apptracuuphim.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.PlayListAdapter;
import com.example.apptracuuphim.adapter.VideoAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.TvApi;
import com.example.apptracuuphim.databinding.ActivityFilmDetailBinding;
import com.example.apptracuuphim.databinding.ActivityVideoBinding;
import com.example.apptracuuphim.listener.VideoListener;
import com.example.apptracuuphim.model.Film.Video;
import com.example.apptracuuphim.model.Tv.Tv;
import com.example.apptracuuphim.resource.FilmResource;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity {
    private ActivityVideoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLifecycle().addObserver(binding.contentVideo.ytbPlayer);
        binding.contentVideo.ytbPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getIntent().getStringExtra("id");
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        binding.contentVideo.videoName.setText(getIntent().getStringExtra("name"));
        binding.contentVideo.vidoeType.setText(getIntent().getStringExtra("type"));

        TvApi.tv.getTvSerieDetails(getIntent().getIntExtra("series_id",37854),"vi")
                .enqueue(new Callback<Tv>() {
                        @Override
                        public void onResponse(Call<Tv> call, Response<Tv> response) {
                            Tv tv = response.body();
                            // Name
                            if (tv.getName().length() < 15){
                                binding.toolbar.toolbarTitle.setText(tv.getName());
                            } else {
                                binding.toolbar.toolbarTitle.setText(tv.getName().substring(0,15) + "...");
                            }
                            binding.contentVideo.tvName.setText(tv.getName());

                            // Date
                            binding.contentVideo.tvDate.setText(tv.getFirst_air_date());
//
                            // Ep
                            binding.contentVideo.tvEp.setText(String.valueOf(tv.getNumber_of_episodes()) + " tập");

                            // Poster
                            if (tv.getPoster_path() != null) {
                                Glide.with(VideoActivity.this).load("https://image.tmdb.org/t/p/original" + tv.getPoster_path()).into(binding.contentVideo.imgTv);
                            }
                        }

                        @Override
                        public void onFailure(Call<Tv> call, Throwable t) {

                        }
                });

        TvApi.tv.getVideoTv(getIntent().getIntExtra("series_id",37854),"en")
                .enqueue(new Callback<FilmResource<Video>>() {
                    @Override
                    public void onResponse(Call<FilmResource<Video>> call, Response<FilmResource<Video>> response) {
                        FilmResource<Video> filmResource = response.body();
                        binding.contentVideo.filmPlaylist.title.setText("Danh sách phát");
                        binding.contentVideo.filmPlaylist.action.setVisibility(View.GONE);
                        binding.contentVideo.filmPlaylist.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(VideoActivity.this)
                        );

                        binding.contentVideo.filmPlaylist.recycleviewFilm.setAdapter(
                                new PlayListAdapter(
                                        VideoActivity.this,
                                        filmResource.getResults(),
                                        new VideoListener() {
                                            @Override
                                            public void onClick(Video video) {
                                                Intent intent = new Intent(VideoActivity.this, VideoActivity.class);
                                                intent.putExtra("id",video.getKey());
                                                intent.putExtra("series_id",getIntent().getIntExtra("series_id",37854));
                                                intent.putExtra("name",video.getName());
                                                intent.putExtra("type",video.getType());
                                                startActivity(intent);
                                            }
                                        }
                                )
                        );
                    }

                    @Override
                    public void onFailure(Call<FilmResource<Video>> call, Throwable t) {

                    }
                });
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}