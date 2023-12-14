package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.AlbumAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.TvApi;
import com.example.apptracuuphim.databinding.ActivityAlbumBinding;
import com.example.apptracuuphim.databinding.ActivityCastBinding;
import com.example.apptracuuphim.model.Film.ImageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {
    private ActivityAlbumBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlbumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.toolbar.toolbarTitle.setText(getIntent().getStringExtra("name"));
        FilmApi.film.getImages(getIntent().getStringExtra("media_type"),getIntent().getIntExtra("id",37854),"")
                .enqueue(new Callback<ImageType>() {
                        @Override
                        public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                            ImageType imageType = response.body();

                            binding.rcvAlbum.setLayoutManager(
                                    new GridLayoutManager(AlbumActivity.this,2)
                            );

                            binding.rcvAlbum.setAdapter(
                                    new AlbumAdapter(AlbumActivity.this,imageType.getBackdrops())
                            );
                        }

                        @Override
                        public void onFailure(Call<ImageType> call, Throwable t) {

                        }
                });
    }
}