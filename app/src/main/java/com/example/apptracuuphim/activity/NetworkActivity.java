package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.adapter.ExtraDetailAdapter;
import com.example.apptracuuphim.adapter.ItemAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.NetworkApi;
import com.example.apptracuuphim.databinding.ActivityNetworkBinding;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.model.Film.ExtraInfo;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Network.Network;
import com.example.apptracuuphim.resource.FilmResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkActivity extends AppCompatActivity implements Callback<Network>{
    private ActivityNetworkBinding binding;
    private List<Film> filmList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetworkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Chi tiết
        NetworkApi.network.getNetworkDetail(getIntent().getIntExtra("id",37854)).enqueue(this);
    }

    @Override
    public void onResponse(Call<Network> call, Response<Network> response) {
        Network network = response.body();

        // Name
        binding.toolbar.toolbarTitle.setText(network.getName());
        binding.contentNetwork.name.setText(network.getName());

        // Logo
        Glide.with(this).load("https://image.tmdb.org/t/p/original" + network.getLogo_path()).into(binding.contentNetwork.ivMedia);
        binding.contentNetwork.itemMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // HomePage
                try {
                    if (!network.getHomepage().equals("") && network.getHomepage() != null) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(network.getHomepage()));
                        startActivity(myIntent);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        // Chi tiết
        binding.contentNetwork.filmExtraInfo.title.setText("Chi tiết");
        binding.contentNetwork.filmExtraInfo.action.setVisibility(View.GONE);
        binding.contentNetwork.filmExtraInfo.recycleviewFilm.setLayoutManager(
                new LinearLayoutManager(NetworkActivity.this)
        );
        List<ExtraInfo> extraInfos = new ArrayList<>();
        extraInfos.add(new ExtraInfo("Xuất xứ", network.getOrigin_country()));
        extraInfos.add(new ExtraInfo("Trụ sở chính", network.getHeadquarters()));

        ExtraDetailAdapter extraDetailAdapter = new ExtraDetailAdapter(extraInfos);
        binding.contentNetwork.filmExtraInfo.recycleviewFilm.setAdapter(extraDetailAdapter);

        // Phim truyền hình nổi bật

        FilmApi.film.getDiscover("tv","en-US", 1,false,"popularity.desc","",100000,0,0,0,"",String.valueOf(network.id))
                .enqueue(new Callback<FilmResource<Film>>() {
                    @Override
                    public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                        FilmResource<Film> filmResource = response.body();

                        binding.contentNetwork.filmKnownTv.title.setText("Phim truyền hình nổi bật");
                        binding.contentNetwork.filmKnownTv.action.setVisibility(View.GONE);
                        binding.contentNetwork.filmKnownTv.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(NetworkActivity.this, RecyclerView.HORIZONTAL,false)
                        );

                        if (filmResource != null) {
                            for (int i = 0; i < filmResource.getResults().size(); i++) {
                                filmResource.getResults().get(i).setMedia_type("tv");
                                filmList.add(filmResource.getResults().get(i));
                            }
                        }

                        binding.contentNetwork.filmKnownTv.recycleviewFilm.setAdapter(new ItemAdapter(
                                NetworkActivity.this,
                                filmList,
                                "backdrop",
                                500,
                                new FilmClickListener() {
                                    @Override
                                    public void onClickItemMovie(Film film) {
                                        Intent intent = new Intent(NetworkActivity.this, FilmDetailActivity.class);
                                        intent.putExtra("id",film.getId());
                                        intent.putExtra("media_type",film.getMedia_type());
                                        startActivity(intent);
                                    }
                                }
                        ));
                    }

                    @Override
                    public void onFailure(Call<FilmResource<Film>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onFailure(Call<Network> call, Throwable t) {

    }
}