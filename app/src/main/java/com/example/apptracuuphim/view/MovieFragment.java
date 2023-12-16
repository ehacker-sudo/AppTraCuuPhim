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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apptracuuphim.activity.AdvancedSearchActivity;
import com.example.apptracuuphim.activity.FilmDetailActivity;
import com.example.apptracuuphim.adapter.ItemAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.MovieApi;
import com.example.apptracuuphim.api.TvApi;
import com.example.apptracuuphim.databinding.FragmentMovieBinding;
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
 * Use the {@link MovieFragment} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    private FragmentMovieBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMovieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        MovieApi.movie.getTopRatedMovies("en",1,"")
                .enqueue(new Callback<FilmResource<Film>>() {
                    @Override
                    public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                        FilmResource<Film> filmResource = response.body();
                        List<Film> filmList = new ArrayList<>();
                        for (int i = 0; i < filmResource.getResults().size(); i++) {
                            filmResource.getResults().get(i).setMedia_type("movie");
                            filmList.add(filmResource.getResults().get(i));
                        }

                        binding.movieTop10.title.setText("Top phim ảnh");
                        binding.movieTop10.rcvFilm.setLayoutManager(
                                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
                        );
                        binding.movieTop10.rcvFilm.setAdapter(new ItemAdapter(
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
        MovieApi.movie.getUpcomingMovies("en",1).enqueue(new Callback<FilmResource<Film>>() {
            @Override
            public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                FilmResource<Film> filmResource = response.body();
                List<Film> filmList = new ArrayList<>();
                for (int i = 0; i < filmResource.getResults().size(); i++) {
                    filmResource.getResults().get(i).setMedia_type("movie");
                    filmList.add(filmResource.getResults().get(i));
                }

                binding.movieUpcoming.title.setText("Sắp ra mắt");
                binding.movieUpcoming.rcvFilm.setLayoutManager(
                        new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
                );
                binding.movieUpcoming.rcvFilm.setAdapter(new ItemAdapter(
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

        MovieApi.movie.getNowPlayingMovies("en",1,"").enqueue(new Callback<FilmResource<Film>>() {
            @Override
            public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                FilmResource<Film> filmResource = response.body();
                List<Film> filmList = new ArrayList<>();
                for (int i = 0; i < filmResource.getResults().size(); i++) {
                    filmResource.getResults().get(i).setMedia_type("movie");
                    filmList.add(filmResource.getResults().get(i));
                }

                binding.moviePlaying.title.setText("Đang được công chiếu");
                binding.moviePlaying.rcvFilm.setLayoutManager(
                        new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
                );
                binding.moviePlaying.rcvFilm.setAdapter(new ItemAdapter(
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

        binding.searchView.searchFilm.setHint("Tìm kiếm phim ảnh");
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
                    binding.movieUpcoming.getRoot().setVisibility(View.GONE);
                    binding.moviePlaying.getRoot().setVisibility(View.GONE);
                    String query = String.valueOf(binding.searchView.searchFilm.getText());
                    FilmApi.film.getMultiSearch("movie",query,"",1).enqueue(new Callback<FilmResource<Film>>() {
                        @Override
                        public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                            FilmResource<Film> searchFilmResource = response.body();

                            List<Film> searchList = new ArrayList<>();
                            for (int i = 0; i < searchFilmResource.getResults().size(); i++) {
                                searchFilmResource.getResults().get(i).setMedia_type("movie");
                                searchList.add(searchFilmResource.getResults().get(i));
                            }

                            binding.movieTop10.title.setText("Đã tìm thấy " + String.valueOf(searchFilmResource.getResults().size()) + " kết quả cho " + "\"" + binding.searchView.searchFilm.getText() +"\"");
                            binding.movieTop10.rcvFilm.setLayoutManager(
                                    new GridLayoutManager(getContext(),2)
                            );
                            binding.movieTop10.rcvFilm.setAdapter(new ItemAdapter(
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