package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apptracuuphim.adapter.ItemAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.databinding.ActivityAllItemBinding;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.resource.FilmResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllItemActivity extends AppCompatActivity implements Callback<FilmResource<Film>>{
    private ActivityAllItemBinding binding;
    private String media_type;
    private String with_genres;
    private int with_runtime_lte;
    private int with_runtime_gte;
    private int vote_average_gte;
    private int vote_count_gte;
    private String with_keywords;
    private List<Film> filmList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        with_genres = getIntent().getStringExtra("with_genres");
        media_type = getIntent().getStringExtra("media_type");
        with_runtime_lte = getIntent().getIntExtra("with_runtime_lte",100000);
        with_runtime_gte = getIntent().getIntExtra("with_runtime_gte",0);
        vote_average_gte = getIntent().getIntExtra("vote_average_gte",0);
        vote_count_gte = getIntent().getIntExtra("vote_count_gte",0);
        with_keywords = getIntent().getStringExtra("with_keywords");

        binding.toolbar.toolbarTitle.setText("Tìm kiếm phim");

        if (!media_type.isEmpty()) {
            FilmApi.film.getDiscover(media_type,"en-US", 1,false,"popularity.desc",with_genres,with_runtime_lte,with_runtime_gte,vote_average_gte,vote_count_gte,with_keywords,"").enqueue(this);
        }


        binding.allItem.recycleviewItem.setAdapter(new ItemAdapter(
                AllItemActivity.this,
                filmList,
                "backdrop",
                new FilmClickListener() {
                    @Override
                    public void onClickItemMovie(Film film) {
                        Intent intent = new Intent(AllItemActivity.this, FilmDetailActivity.class);
                        intent.putExtra("id",film.getId());
                        intent.putExtra("media_type",film.getMedia_type());
                        startActivity(intent);
                    }
                }
        ));
    }

    @Override
    public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
        FilmResource<Film> filmResource = response.body();

        binding.allItem.title.setText(String.valueOf(filmResource.getResults().size()) + " kết quả");
        binding.allItem.recycleviewItem.setLayoutManager(
                new GridLayoutManager(AllItemActivity.this, 2)
        );

        if (filmResource != null) {
            for (int i = 0; i < filmResource.getResults().size(); i++) {
                filmResource.getResults().get(i).setMedia_type(media_type);
                filmList.add(filmResource.getResults().get(i));
            }
        }
    }

    @Override
    public void onFailure(Call<FilmResource<Film>> call, Throwable t) {

    }
}