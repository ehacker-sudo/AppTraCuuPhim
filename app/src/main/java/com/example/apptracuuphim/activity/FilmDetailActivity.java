package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.AlbumAdapter;
import com.example.apptracuuphim.adapter.CastAdapter;
import com.example.apptracuuphim.adapter.CompanyAdapter;
import com.example.apptracuuphim.adapter.ExtraDetailAdapter;
import com.example.apptracuuphim.adapter.GenresAdapter;
import com.example.apptracuuphim.adapter.ItemAdapter;
import com.example.apptracuuphim.adapter.NetworkAdapter;
import com.example.apptracuuphim.adapter.SocialMediaAdapter;
import com.example.apptracuuphim.adapter.VideoAdapter;
import com.example.apptracuuphim.api.CompanyApi;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.NetworkApi;
import com.example.apptracuuphim.listener.CastListener;
import com.example.apptracuuphim.api.MovieApi;
import com.example.apptracuuphim.api.TvApi;
import com.example.apptracuuphim.databinding.ActivityFilmDetailBinding;
import com.example.apptracuuphim.listener.CompanyListener;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.listener.GenresListener;
import com.example.apptracuuphim.listener.MiniItemListener;
import com.example.apptracuuphim.listener.NetworkListener;
import com.example.apptracuuphim.listener.SocialMediaListener;
import com.example.apptracuuphim.listener.VideoListener;
import com.example.apptracuuphim.model.Company.Company;
import com.example.apptracuuphim.model.Credit.Cast;
import com.example.apptracuuphim.model.Film.ExtraInfo;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.Genres;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.Film.Keywords;
import com.example.apptracuuphim.model.Film.Video;
import com.example.apptracuuphim.model.Movie.Movie;
import com.example.apptracuuphim.model.Network.Network;
import com.example.apptracuuphim.model.SocialMedia.Media;
import com.example.apptracuuphim.model.SocialMedia.SocialMedia;
import com.example.apptracuuphim.model.Tv.Tv;
import com.example.apptracuuphim.resource.CreditsResource;
import com.example.apptracuuphim.resource.FilmResource;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmDetailActivity extends AppCompatActivity {
    private ActivityFilmDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilmDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (getIntent().getStringExtra("media_type").equals("tv")) {
            TvApi.tv.getTvSerieDetails(getIntent().getIntExtra("id",37854),"vi")
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
                            binding.contentFilm.tvFilmName.setText(tv.getName());
                            // Rate
                            binding.contentFilm.tvMovieRate.setText(Double.toString(tv.getVote_average()));
                            // Data
                            binding.contentFilm.tvMovieDate.setText(tv.getFirst_air_date());
                            // Poster
                            Glide.with(FilmDetailActivity.this)
                                 .load("https://image.tmdb.org/t/p/w500" + tv.getPoster_path())
                                 .into(binding.contentFilm.imgMovie);

                            // Overview
                            binding.contentFilm.filmOverview.filmTitle.setText("Giới thiệu phim");
                            binding.contentFilm.filmOverview.tvFilm.setText(tv.getOverview());

                            // Chi tiết
                            binding.contentFilm.filmExtraInfo.title.setText("Chi tiết");
                            binding.contentFilm.filmExtraInfo.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this)
                            );
                            List<ExtraInfo> extraInfos = new ArrayList<>();
                            if (tv.getEpisode_run_time().length != 0) {
                                extraInfos.add(new ExtraInfo("Thời lượng các tập",String.valueOf(tv.getEpisode_run_time()[0]) + " phút"));
                            }
                            extraInfos.add(new ExtraInfo(
                                    "Các mùa phim",
                                    String.valueOf(tv.getNumber_of_seasons()),
                                    new MiniItemListener() {
                                        @Override
                                        public void onclick() {
                                            Intent intent = new Intent(FilmDetailActivity.this, SeasonActivity.class);
                                            intent.putExtra("id",tv.getId());
                                            intent.putExtra("name",tv.getName());
                                            startActivity(intent);
                                        }
                                    }));
                            extraInfos.add(new ExtraInfo(
                                    "Các tập phim",
                                    String.valueOf(tv.getNumber_of_episodes()),
                                    new MiniItemListener() {
                                        @Override
                                        public void onclick() {
                                            Intent intent = new Intent(FilmDetailActivity.this, SeasonActivity.class);
                                            intent.putExtra("id",tv.getId());
                                            intent.putExtra("name",tv.getName());
                                            startActivity(intent);
                                        }
                                    }));
                            if (tv.isIn_production() && tv.getNext_episode_to_air() != null){
                                //  Next Episode
                                extraInfos.add(new ExtraInfo(
                                        "Tập phim tiếp theo",
                                        tv.getNext_episode_to_air().getAir_date(),
                                        new MiniItemListener() {
                                            @Override
                                            public void onclick() {
                                                Intent intent = new Intent(FilmDetailActivity.this, EpisodeActivity.class);
                                                intent.putExtra("series_id",tv.getId());
                                                intent.putExtra("season_number",tv.getNext_episode_to_air().getSeason_number());
                                                intent.putExtra("id",tv.getNext_episode_to_air().getEpisode_number());
                                                startActivity(intent);
                                            }
                                        }
                                ));
                            }
                            ExtraDetailAdapter extraDetailAdapter = new ExtraDetailAdapter(extraInfos);
                            binding.contentFilm.filmExtraInfo.recycleviewFilm.setAdapter(extraDetailAdapter);

                            // Genres
                            binding.contentFilm.filmGenres.title.setText("Thể loại");
                            binding.contentFilm.filmGenres.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            binding.contentFilm.filmGenres.recycleviewFilm.setAdapter(
                                    new GenresAdapter(
                                            tv.getGenres(),
                                            new GenresListener() {
                                                @Override
                                                public void onClick(Genres genres) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, AllItemActivity.class);
                                                    intent.putExtra("media_type","tv");
                                                    intent.putExtra("with_genres",String.valueOf(genres.getId()));
                                                    startActivity(intent);
                                                }
                                            }
                                    )
                            );

                            // Network
                            binding.contentFilm.filmNetwork.title.setText("Kênh truyền hình");
                            binding.contentFilm.filmNetwork.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            List<Network> networkList = new ArrayList<>();
                            for (Network network: tv.getNetworks()) {
                                if (network.getLogo_path() != null && !network.getLogo_path().equals("")) {
                                    networkList.add(network);
                                }
                            }
                            binding.contentFilm.filmNetwork.recycleviewFilm.setAdapter(
                                    new NetworkAdapter(
                                            FilmDetailActivity.this,
                                            networkList,
                                            new NetworkListener() {
                                                @Override
                                                public void onClick(Network network) {
                                                    try {
                                                        NetworkApi.network.getNetworkDetail(network.getId())
                                                                .enqueue(new Callback<Network>() {
                                                                    @Override
                                                                    public void onResponse(Call<Network> call, Response<Network> response) {
                                                                        Network network1 = response.body();
                                                                        if (!network1.getHomepage().equals("") && network1.getHomepage() != null) {
                                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(network1.getHomepage()));
                                                                            startActivity(myIntent);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Network> call, Throwable t) {
                                                                        Toast.makeText(getApplicationContext(), "Api Error",  Toast.LENGTH_LONG).show();
                                                                        t.printStackTrace();
                                                                    }
                                                                });
                                                    } catch (ActivityNotFoundException e) {
                                                        Toast.makeText(getApplicationContext(), "No application can handle this request."
                                                                + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                                                        e.printStackTrace();
                                                    }
                                                }
                                            })
                            );

                            // Company
                            binding.contentFilm.filmCompany.title.setText("Các công ty sản xuât");
                            binding.contentFilm.filmCompany.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this,RecyclerView.HORIZONTAL,false)
                            );
                            List<Company> companyList = new ArrayList<>();
                            for (Company company: tv.getProduction_companies()) {
                                if (company.getLogo_path() != null && !company.getLogo_path().equals("")) {
                                    companyList.add(company);
                                }
                            }
                            binding.contentFilm.filmCompany.recycleviewFilm.setAdapter(
                                    new CompanyAdapter(
                                            FilmDetailActivity.this,
                                            companyList,
                                            new CompanyListener() {
                                                @Override
                                                public void onClick(Company company) {
                                                    try {
                                                        CompanyApi.company.getCompanyDetail(company.getId())
                                                                .enqueue(new Callback<Company>() {
                                                                    @Override
                                                                    public void onResponse(Call<Company> call, Response<Company> response) {
                                                                        Company company1 = response.body();
                                                                        if (!company1.getHomepage().equals("") && company1.getHomepage() != null) {
                                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(company1.getHomepage()));
                                                                            startActivity(myIntent);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Company> call, Throwable t) {
                                                                        Toast.makeText(getApplicationContext(), "Api Error",  Toast.LENGTH_LONG).show();
                                                                        t.printStackTrace();
                                                                    }
                                                                });
                                                    } catch (ActivityNotFoundException e) {
                                                        Toast.makeText(getApplicationContext(), "No application can handle this request."
                                                                + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                                                        e.printStackTrace();
                                                    }
                                                }
                                            })
                            );

                            binding.contentFilm.filmAlbum.action.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(FilmDetailActivity.this, AlbumActivity.class);
                                    intent.putExtra("id",getIntent().getIntExtra("id",37854));
                                    intent.putExtra("name",tv.getName());
                                    intent.putExtra("media_type",getIntent().getStringExtra("media_type"));
                                    startActivity(intent);
                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<Tv> call, Throwable t) {

                        }
                    });

            TvApi.tv.getTVSeriesImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(new Callback<ImageType>() {
                        @Override
                        public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                            ImageType imageType = response.body();

                            List<SlideModel> imageList = new ArrayList(); // Create image list


                            if (imageType.getBackdrops().size() < 3) {
                                for (int i = 0; i < imageType.getBackdrops().size(); i++) {
                                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(i).getFile_path(), "",ScaleTypes.CENTER_CROP));
                                }
                                if (imageType.getBackdrops().isEmpty()){
                                    binding.contentFilm.imageSlider.setVisibility(View.GONE);
                                }
                            }
                            else {
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(0).getFile_path(), "",ScaleTypes.CENTER_CROP));
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            }

                            ImageSlider imageSlider = binding.contentFilm.imageSlider;
                            imageSlider.setImageList(imageList);

                            binding.contentFilm.filmAlbum.title.setText("Tập ảnh");
                            binding.contentFilm.filmAlbum.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            LinearLayout.LayoutParams image = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            image.setMargins(20, 0, 0, 0);

                            binding.contentFilm.filmAlbum.recycleviewFilm.setAdapter(
                                    new AlbumAdapter(FilmDetailActivity.this,imageType.getBackdrops(),image)
                            );
                        }

                        @Override
                        public void onFailure(Call<ImageType> call, Throwable t) {

                        }
                    });

            TvApi.tv.getVideoTv(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(new Callback<FilmResource<Video>>() {
                        @Override
                        public void onResponse(Call<FilmResource<Video>> call, Response<FilmResource<Video>> response) {
                            FilmResource<Video> filmResource = response.body();
                            binding.contentFilm.filmVideo.title.setText("Video");
                            binding.contentFilm.filmVideo.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            binding.contentFilm.filmVideo.recycleviewFilm.setAdapter(
                                    new VideoAdapter(
                                            FilmDetailActivity.this,
                                            filmResource.getResults(),
                                            new VideoListener() {
                                                @Override
                                                public void onClick(Video video) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, VideoActivity.class);
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

            TvApi.tv.getTvSerieRecommendations(getIntent().getIntExtra("id",37854),"en",1)
                    .enqueue(new Callback<FilmResource<Film>>() {
                        @Override
                        public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                            FilmResource<Film> filmResource = response.body();
                            binding.contentFilm.filmRecommendation.title.setText("Đề xuất");
                            binding.contentFilm.filmRecommendation.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this,LinearLayoutManager.HORIZONTAL,false)
                            );
                            binding.contentFilm.filmRecommendation.recycleviewFilm.setAdapter(
                                    new ItemAdapter(
                                            FilmDetailActivity.this,
                                            filmResource.getResults(),
                                            "backdrop",
                                            500,
                                            new FilmClickListener() {
                                                @Override
                                                public void onClickItemMovie(Film film) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, FilmDetailActivity.class);
                                                    intent.putExtra("id",film.getId());
                                                    intent.putExtra("media_type",film.getMedia_type());
                                                    startActivity(intent);
                                                }
                                            }
                                    )
                            );
                        }

                        @Override
                        public void onFailure(Call<FilmResource<Film>> call, Throwable t) {

                        }
                    });

            TvApi.tv.getTvSerieCredits(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(new Callback<CreditsResource>() {
                        @Override
                        public void onResponse(Call<CreditsResource> call, Response<CreditsResource> response) {
                            CreditsResource creditsResource = response.body();

                            binding.contentFilm.filmCast.title.setText("Diễn viên");
                            binding.contentFilm.filmCast.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            binding.contentFilm.filmCast.recycleviewFilm.setAdapter(
                                    new CastAdapter(
                                            FilmDetailActivity.this,
                                            creditsResource.getCast(),
                                            new CastListener() {
                                                @Override
                                                public void onClick(Cast cast) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, CastActivity.class);
                                                    intent.putExtra("id",cast.getId());
                                                    startActivity(intent);
                                                }
                                            }
                                    ));
                        }

                        @Override
                        public void onFailure(Call<CreditsResource> call, Throwable t) {

                        }
                    });
            FilmApi.film.getExternalIDs("tv",getIntent().getIntExtra("id",37854))
                    .enqueue(new Callback<SocialMedia>() {
                        @Override
                        public void onResponse(Call<SocialMedia> call, Response<SocialMedia> response) {
                            SocialMedia socialMedia = response.body();
                            List<Media> mediaList = new ArrayList<>();
                            if (socialMedia.getImdb_id() != null && !socialMedia.getImdb_id().equals("")) {
                                mediaList.add(new Media("Imdb",socialMedia.getImdb_id(), R.drawable.imdb));
                            }
                            if (socialMedia.getFacebook_id() != null && !socialMedia.getFacebook_id().equals("")) {
                                mediaList.add(new Media("Facebook",socialMedia.getFacebook_id(), R.drawable.facebook));
                            }

                            if (socialMedia.getWikidata_id() != null && !socialMedia.getWikidata_id().equals("")) {
                                mediaList.add(new Media("Wikidata",socialMedia.getWikidata_id(), R.drawable.wikidata));
                            }

                            if (socialMedia.getWikidata_id() != null && !socialMedia.getWikidata_id().equals("")) {
                                mediaList.add(new Media("Instagram",socialMedia.getInstagram_id(), R.drawable.instagram));
                            }

                            if (socialMedia.getWikidata_id() != null && !socialMedia.getWikidata_id().equals("")) {
                                mediaList.add(new Media("Twitter",socialMedia.getTwitter_id(), R.drawable.twitter));
                            }

                            binding.contentFilm.filmSocialMedia.title.setText("Khám phá thêm");
                            binding.contentFilm.filmSocialMedia.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this)
                            );

                            binding.contentFilm.filmSocialMedia.recycleviewFilm.setAdapter(
                                    new SocialMediaAdapter(
                                            FilmDetailActivity.this,
                                            mediaList,
                                            new SocialMediaListener() {
                                                @Override
                                                public void onclick(Media media) {
                                                    try {
                                                        if (media.getName().equals("Imdb")) {
                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/"+media.getExternal_id()));
                                                            startActivity(myIntent);
                                                        } else if (media.getName().equals("Facebook")) {
                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+media.getExternal_id()));
                                                            startActivity(myIntent);
                                                        } else if (media.getName().equals("Wikidata")) {
                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/"+media.getExternal_id()));
                                                            startActivity(myIntent);
                                                        } else if (media.getName().equals("Instagram")) {
                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"+media.getExternal_id()));
                                                            startActivity(myIntent);
                                                        } else if (media.getName().equals("Twitter")) {
                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+media.getExternal_id()));
                                                            startActivity(myIntent);
                                                        }
                                                    } catch (ActivityNotFoundException e) {
                                                        Toast.makeText(getApplicationContext(), "No application can handle this request."
                                                                + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                    ));
                        }

                        @Override
                        public void onFailure(Call<SocialMedia> call, Throwable t) {

                        }
                    });

            binding.contentFilm.filmNetwork.action.setVisibility(View.GONE);

        } else if (getIntent().getStringExtra("media_type").equals("movie")) {
            MovieApi.movie.getMovieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            Movie movie = response.body();
                            if (movie.getTitle().length() < 15){
                                binding.toolbar.toolbarTitle.setText(movie.getTitle());
                            } else {
                                binding.toolbar.toolbarTitle.setText(movie.getTitle().substring(0,15) + "...");
                            }
                            binding.contentFilm.tvFilmName.setText(movie.getTitle());
                            binding.contentFilm.tvMovieDate.setText(movie.getRelease_date());
                            binding.contentFilm.tvMovieRate.setText(Double.toString(movie.getVote_average()));
                            Glide.with(FilmDetailActivity.this)
                                    .load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                                    .into(binding.contentFilm.imgMovie);

                            // Overview
                            binding.contentFilm.filmOverview.filmTitle.setText("Giới thiệu phim");
                            binding.contentFilm.filmOverview.tvFilm.setText(movie.getOverview());

//                            // Chi tiết
                            binding.contentFilm.filmExtraInfo.title.setText("Chi tiết");
                            binding.contentFilm.filmExtraInfo.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this)
                            );
                            List<ExtraInfo> extraInfos = new ArrayList<>();
                            extraInfos.add(new ExtraInfo("Ngân sách",String.valueOf(movie.getBudget())));
                            extraInfos.add(new ExtraInfo("Thời lượng",String.valueOf(movie.getRuntime()) + " phút"));
                            extraInfos.add(new ExtraInfo("Doanh thu",String.valueOf(movie.getRevenue())));
                            if (movie.getBelongs_to_collection() != null) {
                                extraInfos.add(new ExtraInfo(
                                        "Trọn bộ các phần phim",
                                        movie.getBelongs_to_collection().getName(),
                                        new MiniItemListener() {
                                            @Override
                                            public void onclick() {


                                            }
                                        }));
                            }
                            ExtraDetailAdapter extraDetailAdapter = new ExtraDetailAdapter(extraInfos);
                            binding.contentFilm.filmExtraInfo.recycleviewFilm.setAdapter(extraDetailAdapter);

                            // Genres
                            binding.contentFilm.filmGenres.title.setText("Thể loại");
                            binding.contentFilm.filmGenres.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            binding.contentFilm.filmGenres.recycleviewFilm.setAdapter(
                                    new GenresAdapter(
                                            movie.getGenres(),
                                            new GenresListener() {
                                                @Override
                                                public void onClick(Genres genres) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, AllItemActivity.class);
                                                    intent.putExtra("media_type","movie");
                                                    intent.putExtra("with_genres",String.valueOf(genres.getId()));
                                                    startActivity(intent);
                                                }
                                            }
                                    )
                            );

                            // Company
                            binding.contentFilm.filmCompany.title.setText("Các công ty sản xuât");
                            binding.contentFilm.filmCompany.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this,RecyclerView.HORIZONTAL,false)
                            );
                            List<Company> companyList = new ArrayList<>();
                            for (Company company: movie.getProduction_companies()) {
                                if (company.getLogo_path() != null && !company.getLogo_path().equals("")) {
                                    companyList.add(company);
                                }
                            }
                            binding.contentFilm.filmCompany.recycleviewFilm.setAdapter(
                                    new CompanyAdapter(
                                            FilmDetailActivity.this,
                                            companyList,
                                            new CompanyListener() {
                                                @Override
                                                public void onClick(Company company) {
                                                    try {
                                                        CompanyApi.company.getCompanyDetail(company.getId())
                                                                .enqueue(new Callback<Company>() {
                                                                    @Override
                                                                    public void onResponse(Call<Company> call, Response<Company> response) {
                                                                        Company company1 = response.body();
                                                                        if (!company1.getHomepage().equals("") && company1.getHomepage() != null) {
                                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(company1.getHomepage()));
                                                                            startActivity(myIntent);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Company> call, Throwable t) {
                                                                        Toast.makeText(getApplicationContext(), "Api Error",  Toast.LENGTH_LONG).show();
                                                                        t.printStackTrace();
                                                                    }
                                                                });
                                                    } catch (ActivityNotFoundException e) {
                                                        Toast.makeText(getApplicationContext(), "No application can handle this request."
                                                                + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                                                        e.printStackTrace();
                                                    }
                                                }
                                            })
                            );

                            binding.contentFilm.filmAlbum.action.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(FilmDetailActivity.this, AlbumActivity.class);
                                    intent.putExtra("id",getIntent().getIntExtra("id",37854));
                                    intent.putExtra("name",movie.getName());
                                    intent.putExtra("media_type",getIntent().getStringExtra("media_type"));
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {

                        }
                    });

            MovieApi.movie.getMovieImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(new Callback<ImageType>() {
                        @Override
                        public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                            ImageType imageType = response.body();

                            List<SlideModel> imageList = new ArrayList(); // Create image list


                            if (imageType.getBackdrops().size() < 3) {
                                for (int i = 0; i < imageType.getBackdrops().size(); i++) {
                                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(i).getFile_path(), "",ScaleTypes.CENTER_CROP));
                                }
                                if (imageType.getBackdrops().isEmpty()){
                                    binding.contentFilm.imageSlider.setVisibility(View.GONE);
                                }
                            }
                            else {
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(0).getFile_path(), "",ScaleTypes.CENTER_CROP));
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            }

                            ImageSlider imageSlider = binding.contentFilm.imageSlider;
                            imageSlider.setImageList(imageList);

                            binding.contentFilm.filmAlbum.title.setText("Tập ảnh");
                            binding.contentFilm.filmAlbum.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            LinearLayout.LayoutParams image = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            image.setMargins(20, 0, 0, 0);

                            binding.contentFilm.filmAlbum.recycleviewFilm.setAdapter(
                                    new AlbumAdapter(FilmDetailActivity.this,imageType.getBackdrops(),image)
                            );
                        }

                        @Override
                        public void onFailure(Call<ImageType> call, Throwable t) {

                        }
                    });

            MovieApi.movie.getMovieRecommendations(getIntent().getIntExtra("id",37854),"en",1)
                    .enqueue(new Callback<FilmResource<Film>>() {
                        @Override
                        public void onResponse(Call<FilmResource<Film>> call, Response<FilmResource<Film>> response) {
                            FilmResource<Film> filmResource = response.body();
                            binding.contentFilm.filmRecommendation.title.setText("Đề xuất");
                            binding.contentFilm.filmRecommendation.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this,LinearLayoutManager.HORIZONTAL,false)
                            );
                            binding.contentFilm.filmRecommendation.recycleviewFilm.setAdapter(
                                    new ItemAdapter(
                                            FilmDetailActivity.this,
                                            filmResource.getResults(),
                                            "backdrop",
                                            500,
                                            new FilmClickListener() {
                                                @Override
                                                public void onClickItemMovie(Film film) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, FilmDetailActivity.class);
                                                    intent.putExtra("id",film.getId());
                                                    intent.putExtra("media_type",film.getMedia_type());
                                                    startActivity(intent);
                                                }
                                            }
                                    )
                            );
                        }

                        @Override
                        public void onFailure(Call<FilmResource<Film>> call, Throwable t) {

                        }
                    });

            MovieApi.movie.getMovieCredits(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(new Callback<CreditsResource>() {
                        @Override
                        public void onResponse(Call<CreditsResource> call, Response<CreditsResource> response) {
                            CreditsResource creditsResource = response.body();

                            binding.contentFilm.filmCast.title.setText("Diễn viên");
                            binding.contentFilm.filmCast.recycleviewFilm.setLayoutManager(
                                    new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                            );

                            binding.contentFilm.filmCast.recycleviewFilm.setAdapter(
                                    new CastAdapter(
                                            FilmDetailActivity.this,
                                            creditsResource.getCast(),
                                            new CastListener() {
                                                @Override
                                                public void onClick(Cast cast) {
                                                    Intent intent = new Intent(FilmDetailActivity.this, CastActivity.class);
                                                    intent.putExtra("id",cast.getId());
                                                    startActivity(intent);
                                                }
                                            }
                                    ));
                        }

                        @Override
                        public void onFailure(Call<CreditsResource> call, Throwable t) {

                        }
                    });

            MovieApi.movie.getMovieExternalIDs(getIntent().getIntExtra("id",37854))
                            .enqueue(new Callback<SocialMedia>() {
                                @Override
                                public void onResponse(Call<SocialMedia> call, Response<SocialMedia> response) {
                                    SocialMedia socialMedia = response.body();
                                    List<Media> mediaList = new ArrayList<>();
                                    if (socialMedia.getImdb_id() != null && !socialMedia.getImdb_id().equals("")) {
                                        mediaList.add(new Media("Imdb",socialMedia.getImdb_id(), R.drawable.imdb));
                                    }
                                    if (socialMedia.getFacebook_id() != null && !socialMedia.getFacebook_id().equals("")) {
                                        mediaList.add(new Media("Facebook",socialMedia.getFacebook_id(), R.drawable.facebook));
                                    }

                                    if (socialMedia.getWikidata_id() != null && !socialMedia.getWikidata_id().equals("")) {
                                        mediaList.add(new Media("Wikidata",socialMedia.getWikidata_id(), R.drawable.wikidata));
                                    }

                                    if (socialMedia.getWikidata_id() != null && !socialMedia.getWikidata_id().equals("")) {
                                        mediaList.add(new Media("Instagram",socialMedia.getInstagram_id(), R.drawable.instagram));
                                    }

                                    if (socialMedia.getWikidata_id() != null && !socialMedia.getWikidata_id().equals("")) {
                                        mediaList.add(new Media("Twitter",socialMedia.getTwitter_id(), R.drawable.twitter));
                                    }

                                    binding.contentFilm.filmSocialMedia.title.setText("Khám phá thêm");
                                    binding.contentFilm.filmSocialMedia.recycleviewFilm.setLayoutManager(
                                            new LinearLayoutManager(FilmDetailActivity.this)
                                    );

                                    binding.contentFilm.filmSocialMedia.recycleviewFilm.setAdapter(
                                            new SocialMediaAdapter(
                                                    FilmDetailActivity.this,
                                                    mediaList,
                                                    new SocialMediaListener() {
                                                        @Override
                                                        public void onclick(Media media) {
                                                            try {
                                                                if (media.getName().equals("Imdb")) {
                                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/"+media.getExternal_id()));
                                                                    startActivity(myIntent);
                                                                } else if (media.getName().equals("Facebook")) {
                                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+media.getExternal_id()));
                                                                    startActivity(myIntent);
                                                                } else if (media.getName().equals("Wikidata")) {
                                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/"+media.getExternal_id()));
                                                                    startActivity(myIntent);
                                                                } else if (media.getName().equals("Instagram")) {
                                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"+media.getExternal_id()));
                                                                    startActivity(myIntent);
                                                                } else if (media.getName().equals("Twitter")) {
                                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+media.getExternal_id()));
                                                                    startActivity(myIntent);
                                                                }
                                                            } catch (ActivityNotFoundException e) {
                                                                Toast.makeText(getApplicationContext(), "No application can handle this request."
                                                                        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                            ));
                                }

                                @Override
                                public void onFailure(Call<SocialMedia> call, Throwable t) {

                                }
                            });
            binding.contentFilm.filmVideo.getRoot().setVisibility(View.GONE);
            binding.contentFilm.filmNetwork.getRoot().setVisibility(View.GONE);
        }


        FilmApi.film.getKeywords(getIntent().getStringExtra("media_type"),getIntent().getIntExtra("id",37854))
                        .enqueue(new Callback<Keywords>() {
                            @Override
                            public void onResponse(Call<Keywords> call, Response<Keywords> response) {
                                Keywords keywords = response.body();
                                List<Genres> keywordList = new ArrayList<>();
                                if (getIntent().getStringExtra("media_type").equals("tv")) {
                                    keywordList = keywords.getResults();
                                } else if (getIntent().getStringExtra("media_type").equals("movie")) {
                                    keywordList = keywords.getKeywords();
                                }
                                // Keyword
                                binding.contentFilm.filmKeyword.title.setText("Từ khóa");
                                binding.contentFilm.filmKeyword.recycleviewFilm.setLayoutManager(
                                        new LinearLayoutManager(FilmDetailActivity.this, RecyclerView.HORIZONTAL,false)
                                );

                                binding.contentFilm.filmKeyword.recycleviewFilm.setAdapter(
                                        new GenresAdapter(
                                                keywordList,
                                                new GenresListener() {
                                                    @Override
                                                    public void onClick(Genres genres) {
                                                        Intent intent = new Intent(FilmDetailActivity.this, AllItemActivity.class);
                                                        intent.putExtra("media_type","movie");
                                                        intent.putExtra("with_keywords",String.valueOf(genres.getId()));
                                                        startActivity(intent);
                                                    }
                                                }
                                        )
                                );
                            }

                            @Override
                            public void onFailure(Call<Keywords> call, Throwable t) {

                            }
                        });

        binding.contentFilm.filmOverview.filmDetailAction.setVisibility(View.GONE);
        binding.contentFilm.filmExtraInfo.action.setVisibility(View.GONE);


        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void asas(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

}