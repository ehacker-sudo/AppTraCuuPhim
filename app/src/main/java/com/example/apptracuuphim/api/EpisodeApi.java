package com.example.apptracuuphim.api;

import com.example.apptracuuphim.model.Film.Episode;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.SocialMedia.SocialMedia;
import com.example.apptracuuphim.resource.CreditsResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EpisodeApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    EpisodeApi episodeApi = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(EpisodeApi.class);

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/season/{season_number}/episode/{episode_number}")
    Call<Episode> getTvEpisodesDetails(
            @Path("series_id") int series_id,
            @Path("season_number") int season_number,
            @Path("episode_number") int episode_number,
            @Query("language") String language
    );
    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/season/{season_number}/episode/{episode_number}/credits")
    Call<CreditsResource> getTvEpisodesCredits(
            @Path("series_id") int series_id,
            @Path("season_number") int season_number,
            @Path("episode_number") int episode_number,
            @Query("language") String language
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/season/{season_number}/episode/{episode_number}/external_ids")
    Call<SocialMedia> getTvEpisodesExternalIDs(
            @Path("series_id") int series_id,
            @Path("season_number") int season_number,
            @Path("episode_number") int episode_number
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/season/{season_number}/episode/{episode_number}/images")
    Call<ImageType> getTvEpisodesImages(
            @Path("series_id") int series_id,
            @Path("season_number") int season_number,
            @Path("episode_number") int episode_number,
            @Query("language") String language
    );
}
