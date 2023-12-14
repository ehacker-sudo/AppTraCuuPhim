package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.apptracuuphim.adapter.ExtraDetailAdapter;
import com.example.apptracuuphim.adapter.ItemAdapter;
import com.example.apptracuuphim.adapter.SocialMediaAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.PersonApi;
import com.example.apptracuuphim.databinding.ActivityCastBinding;
import com.example.apptracuuphim.databinding.ActivityEpisodeBinding;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.listener.MiniItemListener;
import com.example.apptracuuphim.listener.SocialMediaListener;
import com.example.apptracuuphim.model.Film.ExtraInfo;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.Film.People;
import com.example.apptracuuphim.model.SocialMedia.Media;
import com.example.apptracuuphim.model.SocialMedia.SocialMedia;
import com.example.apptracuuphim.resource.FilmCreditResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastActivity extends AppCompatActivity {
    private ActivityCastBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PersonApi.person.getPeopleDetail(getIntent().getIntExtra("id",65510),"en").enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                People people = response.body();
                binding.contentCast.tvCastName.setText(people.getName());
                if (people.getName().length() < 15){
                    binding.toolbar.toolbarTitle.setText(people.getName());
                } else {
                    binding.toolbar.toolbarTitle.setText(people.getName().substring(0,15) + "...");
                }

                List<String> peopleInfoList = new ArrayList<>();
                if (!people.getAlso_known_as().isEmpty()) {
                    peopleInfoList.addAll(people.getAlso_known_as());
                }

                if (!peopleInfoList.isEmpty()) {
                    String alternative_name = "";
                    for (String name : peopleInfoList) {
                        alternative_name = alternative_name + name + " ";
                    }
                    binding.contentCast.textViewCastInfo.setText(alternative_name);
                }
                else {
                    binding.contentCast.textViewCastInfo.setText("");
                }

                String desc = "";
                if (people.getBiography().length() > 200) {
                    for (int i = 0; i < 200; i++) {
                        desc = desc + people.getBiography().charAt(i);
                    }
                    binding.contentCast.nextImage.setVisibility(View.VISIBLE);
                    binding.contentCast.castMoreDesc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CastActivity.this, DescriptionActivity.class);
                            intent.putExtra("bio",people.getBiography());
                            intent.putExtra("name",people.getName());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    desc = people.getBiography();
                    binding.contentCast.nextImage.setVisibility(View.GONE);
                    binding.contentCast.castMoreDesc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                binding.contentCast.tvCastDesc.setText(desc);

                Glide.with(CastActivity.this).load("https://image.tmdb.org/t/p/w500" + people.getProfile_path()).into(binding.contentCast.imgCast);

                PersonApi.person.getPeopleImage(people.getId()).enqueue(new Callback<ImageType>() {
                    @Override
                    public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                        ImageType imageType = response.body();

                        List<SlideModel> imageList = new ArrayList(); // Create image list

                        if (imageType.getProfiles().size() < 3) {
                            for (int i = 0; i < imageType.getProfiles().size(); i++) {
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(i).getFile_path(), "", ScaleTypes.CENTER_CROP));
                            }
                        } else {
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(0).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                        }

                        ImageSlider imageSlider = binding.contentCast.imageSlider;
                        imageSlider.setImageList(imageList);

                        binding.contentCast.castAlbum.title.setText("Tập ảnh");
                        binding.contentCast.castAlbum.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(CastActivity.this, RecyclerView.HORIZONTAL,false)
                        );

                        LinearLayout.LayoutParams image = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        image.setMargins(20, 0, 0, 0);

                        binding.contentCast.castAlbum.recycleviewFilm.setAdapter(
                                new AlbumAdapter(CastActivity.this,imageType.getProfiles(),image)
                        );

                        // Chi tiết
                        binding.contentCast.castExtraInfo.action.setVisibility(View.GONE);
                        binding.contentCast.castExtraInfo.title.setText("Chi tiết");
                        binding.contentCast.castExtraInfo.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(CastActivity.this)
                        );
                        List<ExtraInfo> extraInfos = new ArrayList<>();
                        extraInfos.add(new ExtraInfo("Ngày sinh", people.getBirthday()));
                        if (people.getDeathday() != null) {
                            extraInfos.add(new ExtraInfo("Ngày mất", people.getDeathday()));
                        }
                        Toast.makeText(CastActivity.this, people.getDeathday(), Toast.LENGTH_SHORT).show();
                        if (people.getGender() == 1) {
                            extraInfos.add(new ExtraInfo("Giới tính", "nữ"));
                        } else if (people.getGender() == 2) {
                            extraInfos.add(new ExtraInfo("Giới tính", "nam"));
                        }
                        extraInfos.add(new ExtraInfo("Nơi sinh", people.getPlace_of_birth()));
                        extraInfos.add(new ExtraInfo("Được biết đến với vai trò",people.getKnown_for_department()));
                        ExtraDetailAdapter extraDetailAdapter = new ExtraDetailAdapter(extraInfos);
                        binding.contentCast.castExtraInfo.recycleviewFilm.setAdapter(extraDetailAdapter);
                    }

                    @Override
                    public void onFailure(Call<ImageType> call, Throwable t) {

                    }
                });

                FilmApi.film.getPeopleFilmCredits("tv_credits",people.getId(),"en").enqueue(new Callback<FilmCreditResource>() {
                    @Override
                    public void onResponse(Call<FilmCreditResource> call, Response<FilmCreditResource> response) {
                        FilmCreditResource filmCreditResource = response.body();
                        binding.contentCast.castTvCredits.title.setText("Phim truyền hình nổi bật");
                        binding.contentCast.castTvCredits.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(CastActivity.this,RecyclerView.HORIZONTAL,false)
                        );

                        if (filmCreditResource != null) {
                            List<Film> tvList = new ArrayList<>();
                            for (int i = 0; i < filmCreditResource.getCast().size(); i++) {
                                filmCreditResource.getCast().get(i).setMedia_type("tv");
                                tvList.add(filmCreditResource.getCast().get(i));
                            }
                            binding.contentCast.castTvCredits.recycleviewFilm.setAdapter(
                                    new ItemAdapter(
                                            CastActivity.this,
                                            tvList,
                                            "backdrop",
                                            500,
                                            new FilmClickListener() {
                                                @Override
                                                public void onClickItemMovie(Film film) {
                                                    Intent intent = new Intent(CastActivity.this, FilmDetailActivity.class);
                                                    intent.putExtra("id",film.getId());
                                                    intent.putExtra("media_type",film.getMedia_type());
                                                    startActivity(intent);
                                                }
                                            }
                                    )
                            );
                        }
                        else {
                            binding.contentCast.castTvCredits.recycleviewFilm.setAdapter(
                                    new ItemAdapter(
                                            CastActivity.this,
                                            new ArrayList<>(),
                                            "backdrop",
                                            500,
                                            new FilmClickListener() {
                                                @Override
                                                public void onClickItemMovie(Film film) {

                                                }
                                            }
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmCreditResource> call, Throwable t) {

                    }
                });

                FilmApi.film.getPeopleFilmCredits("movie_credits",people.getId(),"en").enqueue(new Callback<FilmCreditResource>() {
                    @Override
                    public void onResponse(Call<FilmCreditResource> call, Response<FilmCreditResource> response) {
                        FilmCreditResource filmCreditResource = response.body();
                        binding.contentCast.castMovieCredits.title.setText("Phim ảnh nổi bật");
                        binding.contentCast.castMovieCredits.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(CastActivity.this,RecyclerView.HORIZONTAL,false)
                        );

                        if (filmCreditResource != null) {

                            List<Film> movieList = new ArrayList<>();
                            for (int i = 0; i < filmCreditResource.getCast().size(); i++) {
                                filmCreditResource.getCast().get(i).setMedia_type("movie");
                                movieList.add(filmCreditResource.getCast().get(i));
                            }

                            binding.contentCast.castMovieCredits.recycleviewFilm.setAdapter(
                                    new ItemAdapter(
                                            CastActivity.this,
                                            movieList,
                                            "backdrop",
                                            500,
                                            new FilmClickListener() {
                                                @Override
                                                public void onClickItemMovie(Film film) {
                                                    Intent intent = new Intent(CastActivity.this, FilmDetailActivity.class);
                                                    intent.putExtra("id",film.getId());
                                                    intent.putExtra("media_type",film.getMedia_type());
                                                    startActivity(intent);
                                                }
                                            }
                                    )
                            );
                        }
                        else {
                            binding.contentCast.castMovieCredits.recycleviewFilm.setAdapter(
                                    new ItemAdapter(
                                            CastActivity.this,
                                            new ArrayList<>(),
                                            "backdrop",
                                            500,
                                            new FilmClickListener() {
                                                @Override
                                                public void onClickItemMovie(Film film) {

                                                }
                                            }
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmCreditResource> call, Throwable t) {

                    }
                });

                FilmApi.film.getExternalIDs("person",people.getId())
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

                                if (socialMedia.getTiktok_id() != null && !socialMedia.getTiktok_id().equals("")) {
                                    mediaList.add(new Media("Tiktok",socialMedia.getTwitter_id(), R.drawable.tiktok));
                                }

                                if (socialMedia.getYoutube_id() != null && !socialMedia.getYoutube_id().equals("")) {
                                    mediaList.add(new Media("Youtube",socialMedia.getTwitter_id(), R.drawable.youtube));
                                }

                                binding.contentCast.filmSocialMedia.title.setText("Khám phá thêm");
                                binding.contentCast.filmSocialMedia.recycleviewFilm.setLayoutManager(
                                        new LinearLayoutManager(CastActivity.this)
                                );

                                binding.contentCast.filmSocialMedia.recycleviewFilm.setAdapter(
                                        new SocialMediaAdapter(
                                                CastActivity.this,
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
                                                            } else if (media.getName().equals("Tiktok")) {
                                                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/@"+media.getExternal_id()));
                                                                startActivity(myIntent);
                                                            } else if (media.getName().equals("Youtube")) {
                                                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@"+media.getExternal_id()));
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



            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }
}