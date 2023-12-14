package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.CompanyAdapter;
import com.example.apptracuuphim.adapter.EpisodeAdapter;
import com.example.apptracuuphim.adapter.ExtraDetailAdapter;
import com.example.apptracuuphim.adapter.NetworkAdapter;
import com.example.apptracuuphim.adapter.SeasonAdapter;
import com.example.apptracuuphim.api.TvApi;
import com.example.apptracuuphim.databinding.ActivityEpisodeBinding;
import com.example.apptracuuphim.databinding.ActivitySeasonBinding;
import com.example.apptracuuphim.listener.CompanyListener;
import com.example.apptracuuphim.listener.EpisodeListener;
import com.example.apptracuuphim.listener.MiniItemListener;
import com.example.apptracuuphim.listener.NetworkListener;
import com.example.apptracuuphim.listener.SeasonListener;
import com.example.apptracuuphim.model.Company.Company;
import com.example.apptracuuphim.model.Film.Episode;
import com.example.apptracuuphim.model.Film.ExtraInfo;
import com.example.apptracuuphim.model.Network.Network;
import com.example.apptracuuphim.model.Tv.Season;
import com.example.apptracuuphim.model.Tv.Tv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonActivity extends AppCompatActivity {
    private ActivitySeasonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeasonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            TvApi.tv.getTvSeasonsDetails(getIntent().getIntExtra("id",37854),1,"")
                    .enqueue(new Callback<Season>() {
                            @Override
                            public void onResponse(Call<Season> call, Response<Season> response) {
                                Season season = response.body();
                                binding.contentSeason.recycleviewListEpisode.setLayoutManager(
                                        new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false)
                                );

                                binding.contentSeason.recycleviewListEpisode.setAdapter(
                                        new EpisodeAdapter(
                                                getApplicationContext(),
                                                season.getEpisodes(),
                                                new EpisodeListener() {
                                                    @Override
                                                    public void OnClickListener(Episode episode) {
                                                        Intent intent = new Intent(SeasonActivity.this, EpisodeActivity.class);
                                                        intent.putExtra("series_id",getIntent().getIntExtra("id",37854));
                                                        intent.putExtra("season_number",episode.getSeason_number());
                                                        intent.putExtra("id",episode.getId());
                                                        startActivity(intent);
                                                    }
                                                })
                                );
                            }

                            @Override
                            public void onFailure(Call<Season> call, Throwable t) {

                            }
            });

        TvApi.tv.getTvSerieDetails(getIntent().getIntExtra("id",37854),"vi")
                .enqueue(new Callback<Tv>() {
                    @Override
                    public void onResponse(Call<Tv> call, Response<Tv> response) {
                        Tv tv = response.body();
                        binding.toolbar.toolbarTitle.setText(tv.getName());
                        binding.contentSeason.recycleviewSeason.setLayoutManager(
                                new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false)
                        );
//
                        binding.contentSeason.recycleviewSeason.setAdapter(
                                new SeasonAdapter(
                                        tv.getSeasons(),
                                        new SeasonListener() {
                                            @Override
                                            public void onClick(Season season) {
                                                TvApi.tv.getTvSeasonsDetails(getIntent().getIntExtra("id",37854),season.getSeason_number(),"")
                                                        .enqueue(new Callback<Season>() {
                                                            @Override
                                                            public void onResponse(Call<Season> call, Response<Season> response) {
                                                                Season season = response.body();
                                                                binding.contentSeason.recycleviewListEpisode.setLayoutManager(
                                                                        new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false)
                                                                );

                                                                binding.contentSeason.recycleviewListEpisode.setAdapter(
                                                                        new EpisodeAdapter(
                                                                                getApplicationContext(),
                                                                                season.getEpisodes(),
                                                                                new EpisodeListener() {
                                                                                    @Override
                                                                                    public void OnClickListener(Episode episode) {
                                                                                        Intent intent = new Intent(SeasonActivity.this, EpisodeActivity.class);
                                                                                        intent.putExtra("series_id",getIntent().getIntExtra("id",37854));
                                                                                        intent.putExtra("season_number",episode.getSeason_number());
                                                                                        intent.putExtra("id",episode.getId());
                                                                                        startActivity(intent);
                                                                                    }
                                                                                })
                                                                );
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Season> call, Throwable t) {

                                                            }
                                                        });
                                            }
                                        })
                        );

                    }

                    @Override
                    public void onFailure(Call<Tv> call, Throwable t) {

                    }
                });

    }
}