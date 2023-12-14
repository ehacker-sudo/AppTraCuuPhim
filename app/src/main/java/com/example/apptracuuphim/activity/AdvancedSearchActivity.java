package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.SearchCategoryAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.databinding.ActivityAdvancedSearchBinding;
import com.example.apptracuuphim.listener.SecondCollectListener;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.Genres;
import com.example.apptracuuphim.model.Movie.Movie;
import com.example.apptracuuphim.model.Search.Item;
import com.example.apptracuuphim.model.Tv.Tv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvancedSearchActivity extends AppCompatActivity implements Callback<Film> {
    public ActivityAdvancedSearchBinding binding;
    public String itemGenres = "";
    public int itemWithRuntimeLte = 10000;
    public int itemWithRuntimeGte = 0;
    public String itemMediaType = "";
    public int itemVoteCountGte = 0;
    public int itemVoteAverageGte = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvancedSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.toolbarTitle.setText("Tìm kiếm phim");
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<Genres> genresListMediaType = new ArrayList<>();
        genresListMediaType.add(new Genres("Phim ảnh"));
        genresListMediaType.add(new Genres("Phim truyền hình"));

        List<Item> itemListMediaType = new ArrayList<>();
        itemListMediaType.add(new Item("Kiểu phim",genresListMediaType));

        binding.advancedSearch.recycleView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        binding.advancedSearch.recycleView.setAdapter(
            new SearchCategoryAdapter(
                this,
                itemListMediaType,
                new SecondCollectListener() {
                    private boolean isVisible = false;
                    @Override
                    public void OnClickListener(SearchCategoryAdapter.ViewHolder viewHolder) {
                        if (isVisible){
                            viewHolder.tvDesc.setVisibility(View.GONE);
                            viewHolder.arrowUp.setVisibility(View.GONE);
                            viewHolder.arrowDown.setVisibility(View.VISIBLE);
                            isVisible = false;
                        } else {
                            viewHolder.tvDesc.setVisibility(View.VISIBLE);
                            viewHolder.arrowUp.setVisibility(View.VISIBLE);
                            viewHolder.arrowDown.setVisibility(View.GONE);
                            isVisible = true;
                        }
                    }

                    @Override
                    public void OnCheckBoxListener(String title, List<Genres> stringList) {
                        Item firstItemGenres = new Item(title,stringList);
                        if (stringList.size() == 0 || stringList.size() == 2) {
                            List<Item> itemList = new ArrayList<>();

                            binding.advancedSearch.recycleViewGenres.setLayoutManager(
                                    new LinearLayoutManager(AdvancedSearchActivity.this)
                            );
                            binding.advancedSearch.recycleViewGenres.setAdapter(
                                    new SearchCategoryAdapter(AdvancedSearchActivity.this, itemList,null)
                            );
                        } else {
                            if (stringList.get(0).getName().equals("Phim truyền hình")) {
                                itemMediaType = "tv";
                                FilmApi.film.getGenresList("tv").enqueue(AdvancedSearchActivity.this);
                            } else {
                                itemMediaType = "movie";
                                FilmApi.film.getGenresList("movie").enqueue(AdvancedSearchActivity.this);
                            }
                        }
                    }
                })
        );



        List<Item> itemListWithRunTime = new ArrayList<>();

        List<Genres> group1 = new ArrayList<>();
        group1.add(new Genres("Ít hơn 30 phút"));
        group1.add(new Genres("Ít hơn một tiếng"));
        group1.add(new Genres("Từ một tiếng đến hai tiếng"));
        group1.add(new Genres("Từ hai tiếng đến ba tiếng"));
        group1.add(new Genres("Lớn hơn ba tiếng"));

        itemListWithRunTime.add(new Item("Thời lượng",group1));
        binding.advancedSearch.recycleViewWithRuntime.setLayoutManager(
                new LinearLayoutManager(this)
        );
        binding.advancedSearch.recycleViewWithRuntime.setAdapter(
            new SearchCategoryAdapter(
                this,
                itemListWithRunTime,
                new SecondCollectListener() {
                    private boolean isVisible = false;
                    @Override
                    public void OnClickListener(SearchCategoryAdapter.ViewHolder viewHolder) {
                        if (isVisible){
                            viewHolder.tvDesc.setVisibility(View.GONE);
                            viewHolder.arrowUp.setVisibility(View.GONE);
                            viewHolder.arrowDown.setVisibility(View.VISIBLE);
                            isVisible = false;
                        } else {
                            viewHolder.tvDesc.setVisibility(View.VISIBLE);
                            viewHolder.arrowUp.setVisibility(View.VISIBLE);
                            viewHolder.arrowDown.setVisibility(View.GONE);
                            isVisible = true;
                        }
                    }

                    @Override
                    public void OnCheckBoxListener(String title, List<Genres> stringList) {
                        for (int i = 0; i < stringList.size(); i++) {
                            if (stringList.get(i).getName().equals("Ít hơn 30 phút")) {
//                                if (itemWithRuntimeLte < 30 || itemWithRuntimeLte > 180) {
                                    itemWithRuntimeLte = 30;
//                                }
                                itemWithRuntimeGte = 0;
                            }
                            if (stringList.get(i).getName().equals("Ít hơn một tiếng")) {
//                                if (itemWithRuntimeLte < 60 || itemWithRuntimeLte > 180) {
                                    itemWithRuntimeLte = 60;
//                                }
                                itemWithRuntimeGte = 0;
                            }
                            if (stringList.get(i).getName().equals("Từ một tiếng đến hai tiếng")) {
                                itemWithRuntimeLte = 120;
                                itemWithRuntimeGte = 60;
                            }
                            if (stringList.get(i).getName().equals("Từ hai tiếng đến ba tiếng")) {
                                itemWithRuntimeLte = 180;
                                itemWithRuntimeGte = 120;
                            }
                            if (stringList.get(i).getName().equals("Lớn hơn ba tiếng")) {
                                itemWithRuntimeLte = 1000000;
                                itemWithRuntimeGte = 180;
                            }
                        }
                    }
                })
        );

        List<Item> itemListVoteAverage = new ArrayList<>();

        List<Genres> VoteAverage = new ArrayList<>();
        VoteAverage.add(new Genres("6+ điểm"));
        VoteAverage.add(new Genres("7+ điểm"));
        VoteAverage.add(new Genres("8+ điểm"));
        VoteAverage.add(new Genres("9+ điểm"));

        itemListVoteAverage.add(new Item("Điểm số",VoteAverage));
        binding.advancedSearch.recycleViewVoteAverage.setLayoutManager(
                new LinearLayoutManager(this)
        );
        binding.advancedSearch.recycleViewVoteAverage.setAdapter(
                new SearchCategoryAdapter(
                        this,
                        itemListVoteAverage,
                        new SecondCollectListener() {
                            private boolean isVisible = false;
                            @Override
                            public void OnClickListener(SearchCategoryAdapter.ViewHolder viewHolder) {
                                if (isVisible){
                                    viewHolder.tvDesc.setVisibility(View.GONE);
                                    viewHolder.arrowUp.setVisibility(View.GONE);
                                    viewHolder.arrowDown.setVisibility(View.VISIBLE);
                                    isVisible = false;
                                } else {
                                    viewHolder.tvDesc.setVisibility(View.VISIBLE);
                                    viewHolder.arrowUp.setVisibility(View.VISIBLE);
                                    viewHolder.arrowDown.setVisibility(View.GONE);
                                    isVisible = true;
                                }
                            }

                            @Override
                            public void OnCheckBoxListener(String title, List<Genres> stringList) {
                                for (int i = 0; i < stringList.size(); i++) {
                                    if (stringList.get(i).getName().equals("6+ điểm")) {
                                        itemVoteAverageGte = 6;
                                    }
                                    if (stringList.get(i).getName().equals("7+ điểm")) {
                                        itemVoteAverageGte = 7;
                                    }
                                    if (stringList.get(i).getName().equals("8+ điểm")) {
                                        itemVoteAverageGte = 8;
                                    }
                                    if (stringList.get(i).getName().equals("9+ điểm")) {
                                        itemVoteAverageGte = 9;
                                    }
                                }
                            }
                        })
        );

        List<Item> itemListVoteCount = new ArrayList<>();

        List<Genres> VoteCount = new ArrayList<>();
        VoteCount.add(new Genres("100+ đánh giá"));
        VoteCount.add(new Genres("500+ đánh giá"));
        VoteCount.add(new Genres("1000+ đánh giá"));
        VoteCount.add(new Genres("5000+ đánh giá"));
        VoteCount.add(new Genres("10000+ đánh giá"));
        VoteCount.add(new Genres("15000+ đánh giá"));
        VoteCount.add(new Genres("20000+ đánh giá"));

        itemListVoteCount.add(new Item("Lượng đánh giá",VoteCount));
        binding.advancedSearch.recycleViewVoteCount.setLayoutManager(
                new LinearLayoutManager(this)
        );
        binding.advancedSearch.recycleViewVoteCount.setAdapter(
                new SearchCategoryAdapter(
                        this,
                        itemListVoteCount,
                        new SecondCollectListener() {
                            private boolean isVisible = false;
                            @Override
                            public void OnClickListener(SearchCategoryAdapter.ViewHolder viewHolder) {
                                if (isVisible){
                                    viewHolder.tvDesc.setVisibility(View.GONE);
                                    viewHolder.arrowUp.setVisibility(View.GONE);
                                    viewHolder.arrowDown.setVisibility(View.VISIBLE);
                                    isVisible = false;
                                } else {
                                    viewHolder.tvDesc.setVisibility(View.VISIBLE);
                                    viewHolder.arrowUp.setVisibility(View.VISIBLE);
                                    viewHolder.arrowDown.setVisibility(View.GONE);
                                    isVisible = true;
                                }
                            }

                            @Override
                            public void OnCheckBoxListener(String title, List<Genres> stringList) {
                                for (int i = 0; i < stringList.size(); i++) {
                                    if (stringList.get(i).getName().equals("100+ đánh giá")) {
                                        itemVoteCountGte = 100;
                                    }
                                    if (stringList.get(i).getName().equals("500+ đánh giá")) {
                                        itemVoteCountGte = 500;
                                    }
                                    if (stringList.get(i).getName().equals("1000+ đánh giá")) {
                                        itemVoteCountGte = 1000;
                                    }
                                    if (stringList.get(i).getName().equals("5000+ đánh giá")) {
                                        itemVoteCountGte = 5000;
                                    }
                                    if (stringList.get(i).getName().equals("10000+ đánh giá")) {
                                        itemVoteCountGte = 10000;
                                    }
                                    if (stringList.get(i).getName().equals("15000+ đánh giá")) {
                                        itemVoteCountGte = 15000;
                                    }
                                    if (stringList.get(i).getName().equals("20000+ đánh giá")) {
                                        itemVoteCountGte = 20000;
                                    }

                                }
                            }
                        })
        );
        binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvancedSearchActivity.this, AllItemActivity.class);
                intent.putExtra("media_type",itemMediaType);
                intent.putExtra("with_genres",itemGenres);
                intent.putExtra("with_runtime_lte",itemWithRuntimeLte);
                intent.putExtra("with_runtime_gte",itemWithRuntimeGte);
                intent.putExtra("vote_average_gte",itemVoteAverageGte);
                intent.putExtra("vote_count_gte",itemVoteCountGte);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResponse(Call<Film> call, Response<Film> response) {
        Film film = response.body();
        List<Item> itemList = new ArrayList<>();
        List<Genres> stringList_Genres = new ArrayList<>();
        for (int i = 0; i < film.getGenres().size(); i++) {
            stringList_Genres.add(new Genres(film.getGenres().get(i).getId(),film.getGenres().get(i).getName()));
        }
        itemList.add(new Item("Thể loại",stringList_Genres));

        binding.advancedSearch.recycleViewGenres.setLayoutManager(
                new LinearLayoutManager(AdvancedSearchActivity.this)
        );
        binding.advancedSearch.recycleViewGenres.setAdapter(
            new SearchCategoryAdapter(
                AdvancedSearchActivity.this,
                itemList,
                new SecondCollectListener() {
                    public String with_genres = "";
                    private boolean isVisibleGennes = false;
                    @Override
                    public void OnClickListener(SearchCategoryAdapter.ViewHolder viewHolder) {
                        if (isVisibleGennes){
                            viewHolder.tvDesc.setVisibility(View.GONE);
                            viewHolder.arrowUp.setVisibility(View.GONE);
                            viewHolder.arrowDown.setVisibility(View.VISIBLE);
                            isVisibleGennes = false;
                        } else {
                            viewHolder.tvDesc.setVisibility(View.VISIBLE);
                            viewHolder.arrowUp.setVisibility(View.VISIBLE);
                            viewHolder.arrowDown.setVisibility(View.GONE);
                            isVisibleGennes = true;
                        }
                    }

                    @Override
                    public void OnCheckBoxListener(String title,List<Genres> stringList) {
                        if (!with_genres.equals("")) {
                            with_genres = "";
                        }
                        if (stringList.size() != 0) {
                            for (int i = 0; i < stringList.size() - 1; i++) {
                                with_genres = with_genres + stringList.get(i).getId() + " , ";
                            }
                            with_genres = with_genres + stringList.get(stringList.size() - 1).getId();
                        }
                        itemGenres = with_genres;
                    }
                })
        );
    }

    @Override
    public void onFailure(Call<Film> call, Throwable t) {

    }
}