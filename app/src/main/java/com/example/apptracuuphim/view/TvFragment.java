package com.example.apptracuuphim.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptracuuphim.activity.AdvancedSearchActivity;
import com.example.apptracuuphim.activity.FilmDetailActivity;
import com.example.apptracuuphim.adapter.ItemAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.MovieApi;
import com.example.apptracuuphim.api.TvApi;
import com.example.apptracuuphim.databinding.FragmentTvBinding;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Movie.Movie;
import com.example.apptracuuphim.resource.FilmResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TvFragment} factory method to
 * create an instance of this fragment.
 */
public class TvFragment extends Fragment {
    private FragmentTvBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTvBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        FilmApi.film.getTopRatedFilm("tv","en",1)
                .enqueue(new Callback<FilmResource<Film>>() {
                    @Override
                    public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                        FilmResource<Film> filmResource = response.body();
                        List<Film> filmList = new ArrayList<>();
                        for (int i = 0; i < filmResource.getResults().size(); i++) {
                            filmResource.getResults().get(i).setMedia_type("tv");
                            filmList.add(filmResource.getResults().get(i));
                        }

                        binding.tvTop10.title.setText("Top phim truyền hình");

                        binding.tvTop10.rcvFilm.setLayoutManager(
                                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
                        );
                        binding.tvTop10.rcvFilm.setAdapter(new ItemAdapter(
                                getContext(),
                                filmList,
                                "backdrop",
                                500,
                                new FilmClickListener() {
                                    @Override
                                    public void onClickItemMovie(Film film) {
                                        Intent intent = new Intent(getContext(), FilmDetailActivity.class);
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
        TvApi.tv.getOnAirTvSeries("en",1).enqueue(new Callback<FilmResource<Film>>() {
            @Override
            public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                FilmResource<Film> filmResource = response.body();
                List<Film> filmList = new ArrayList<>();
                for (int i = 0; i < filmResource.getResults().size(); i++) {
                    filmResource.getResults().get(i).setMedia_type("tv");
                    filmList.add(filmResource.getResults().get(i));
                }

                binding.tvOnAir.title.setText("Vẫn còn lên sóng");
                binding.tvOnAir.rcvFilm.setLayoutManager(
                        new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
                );
                binding.tvOnAir.rcvFilm.setAdapter(new ItemAdapter(
                        getContext(),
                        filmList,
                        "poster",
                        300,
                        new FilmClickListener() {
                            @Override
                            public void onClickItemMovie(Film film) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
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

        TvApi.tv.getAiringTodayTvSeries("en",1).enqueue(new Callback<FilmResource<Film>>() {
            @Override
            public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                FilmResource<Film> filmResource = response.body();
                List<Film> filmList = new ArrayList<>();
                for (int i = 0; i < filmResource.getResults().size(); i++) {
                    filmResource.getResults().get(i).setMedia_type("tv");
                    filmList.add(filmResource.getResults().get(i));
                }

                binding.tvAiringToday.title.setText("Đang được lên sóng");
                binding.tvAiringToday.rcvFilm.setLayoutManager(
                        new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
                );
                binding.tvAiringToday.rcvFilm.setAdapter(new ItemAdapter(
                        getContext(),
                        filmList,
                        "backdrop",
                        500,
                        new FilmClickListener() {
                            @Override
                            public void onClickItemMovie(Film film) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
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

        binding.searchView.searchFilm.setHint("Tìm kiếm phim truyền hình");
        Drawable background = binding.searchView.searchFilm.getBackground();
        binding.searchView.searchFilm.setBackgroundColor(Color.TRANSPARENT);
        binding.searchView.searchFilm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.searchView.searchFilm.setBackground(background);
            }
        });

        binding.searchView.searchFilm.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    binding.tvOnAir.getRoot().setVisibility(View.GONE);
                    binding.tvAiringToday.getRoot().setVisibility(View.GONE);
                    String query = String.valueOf(binding.searchView.searchFilm.getText());
                    FilmApi.film.getMultiSearch("tv",query,"",1).enqueue(new Callback<FilmResource<Film>>() {
                        @Override
                        public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                            FilmResource<Film> searchFilmResource = response.body();

                            List<Film> searchList = new ArrayList<>();
                            for (int i = 0; i < searchFilmResource.getResults().size(); i++) {
                                searchFilmResource.getResults().get(i).setMedia_type("tv");
                                searchList.add(searchFilmResource.getResults().get(i));
                            }

                            binding.tvTop10.title.setText("Đã tìm thấy " + String.valueOf(searchList.size()) + " kết quả cho " + "\"" + binding.searchView.searchFilm.getText() +"\"");
                            binding.tvTop10.rcvFilm.setLayoutManager(
                                    new GridLayoutManager(getContext(),2)
                            );
                            binding.tvTop10.rcvFilm.setAdapter(new ItemAdapter(
                                    getContext(),
                                    searchList,
                                    "backdrop",
                                    new FilmClickListener() {
                                        @Override
                                        public void onClickItemMovie(Film film) {
                                            Intent intent = new Intent(getContext(), FilmDetailActivity.class);
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
                    return true;
                }
                return false;
            }
        });

        binding.searchView.searchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdvancedSearchActivity.class);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}