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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.CastAdapter;
import com.example.apptracuuphim.adapter.PeopleAdapter;
import com.example.apptracuuphim.adapter.SocialMediaAdapter;
import com.example.apptracuuphim.api.EpisodeApi;
import com.example.apptracuuphim.databinding.ActivityEpisodeBinding;
import com.example.apptracuuphim.listener.CastListener;
import com.example.apptracuuphim.listener.CreditListener;
import com.example.apptracuuphim.listener.SocialMediaListener;
import com.example.apptracuuphim.model.Credit.Cast;
import com.example.apptracuuphim.model.Credit.Credit;
import com.example.apptracuuphim.model.Film.Episode;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.SocialMedia.Media;
import com.example.apptracuuphim.model.SocialMedia.SocialMedia;
import com.example.apptracuuphim.model.Tv.Tv;
import com.example.apptracuuphim.resource.CreditsResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeActivity extends AppCompatActivity {
    private ActivityEpisodeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEpisodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int series_id = getIntent().getIntExtra("series_id",37854);
        int season_number = getIntent().getIntExtra("season_number",1);
        int episode_number = getIntent().getIntExtra("id",1);
        EpisodeApi.episodeApi.getTvEpisodesDetails(series_id,season_number,episode_number,"en")
                .enqueue(new Callback<Episode>() {
                    @Override
                    public void onResponse(Call<Episode> call, Response<Episode> response) {
                        Episode episode = response.body();

                        if (episode.getName().length() < 15){
                            binding.toolbar.toolbarTitle.setText(episode.getName());
                        } else {
                            binding.toolbar.toolbarTitle.setText(episode.getName().substring(0,15) + "...");
                        }
                        binding.contentEpisode.tvEpName.setText(episode.getName());
                        binding.contentEpisode.tvEpName.setText(episode.getName());

                        // Overview
                        // Overview
                        binding.contentEpisode.filmOverview.filmTitle.setText("Giới thiệu tập phim");
                        binding.contentEpisode.filmOverview.tvFilm.setText(episode.getOverview());
                    }

                    @Override
                    public void onFailure(Call<Episode> call, Throwable t) {

                    }
                });

        EpisodeApi.episodeApi.getTvEpisodesCredits(series_id,season_number,episode_number,"us")
                .enqueue(new Callback<CreditsResource>() {
                    @Override
                    public void onResponse(Call<CreditsResource> call, Response<CreditsResource> response) {
                        CreditsResource creditsResource = response.body();

                        binding.contentEpisode.filmCast.title.setText("Diễn viên");
                        binding.contentEpisode.filmCast.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(EpisodeActivity.this, RecyclerView.HORIZONTAL,false)
                        );

                        binding.contentEpisode.filmCast.recycleviewFilm.setAdapter(
                                new PeopleAdapter(
                                        EpisodeActivity.this,
                                        creditsResource.getCast(),
                                        new CreditListener() {
                                            @Override
                                            public void onClick(Credit credit) {
                                                Intent intent = new Intent(EpisodeActivity.this, CastActivity.class);
                                                intent.putExtra("id",credit.getId());
                                                startActivity(intent);
                                            }
                                        }
                                ));
                    }

                    @Override
                    public void onFailure(Call<CreditsResource> call, Throwable t) {

                    }
                });

        EpisodeApi.episodeApi.getTvEpisodesExternalIDs(series_id,season_number,episode_number)
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

                        binding.contentEpisode.filmSocialMedia.title.setText("Khám phá thêm");
                        binding.contentEpisode.filmSocialMedia.recycleviewFilm.setLayoutManager(
                                new LinearLayoutManager(EpisodeActivity.this)
                        );

                        binding.contentEpisode.filmSocialMedia.recycleviewFilm.setAdapter(
                                new SocialMediaAdapter(
                                        EpisodeActivity.this,
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

        EpisodeApi.episodeApi.getTvEpisodesImages(series_id,season_number,episode_number,"us")
                .enqueue(new Callback<ImageType>() {
                    @Override
                    public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                        ImageType imageType = response.body();

                        List<SlideModel> imageList = new ArrayList(); // Create image list


                        if (imageType.getStills().size() < 3) {
                            for (int i = 0; i < imageType.getStills().size(); i++) {
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getStills().get(i).getFile_path(), "", ScaleTypes.CENTER_CROP));
                            }
                            if (imageType.getStills().isEmpty()){
                                binding.contentEpisode.imageSlider.setVisibility(View.GONE);
                            }
                        }
                        else {
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getStills().get(0).getFile_path(), "", ScaleTypes.CENTER_CROP));
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getStills().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getStills().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                        }

                        ImageSlider imageSlider = binding.contentEpisode.imageSlider;
                        imageSlider.setImageList(imageList);


                        binding.contentEpisode.filmAlbum.title.setText("Tập ảnh");
//                        binding.contentEpisode.filmAlbum.recycleviewFilm.setLayoutManager(
//                                new LinearLayoutManager(EpisodeActivity.this, RecyclerView.HORIZONTAL,false)
//                        );
//
//                        LinearLayout.LayoutParams image = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        image.setMargins(20, 0, 0, 0);
//
//                        binding.contentEpisode.filmAlbum.recycleviewFilm.setAdapter(
//                                new AlbumAdapter(EpisodeActivity.this,imageType.getBackdrops(),image)
//                        );
                    }

                    @Override
                    public void onFailure(Call<ImageType> call, Throwable t) {

                    }
                });

    }
}