package com.example.apptracuuphim.api;

import com.example.apptracuuphim.model.Certification.MovieCertification;
import com.example.apptracuuphim.model.Certification.TvCertification;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.Film.Video;
import com.example.apptracuuphim.model.Tv.Season;
import com.example.apptracuuphim.model.Tv.Tv;
import com.example.apptracuuphim.resource.CreditsResource;
import com.example.apptracuuphim.resource.FilmResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    TvApi tv = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TvApi.class);

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/on_the_air")
    Call<FilmResource<Film>> getOnAirTvSeries(
            @Query("language") String language,
            @Query("page") int page
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/airing_today")
    Call<FilmResource<Film>> getAiringTodayTvSeries(
            @Query("language") String language,
            @Query("page") int page
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}")
    Call<Tv> getTvSerieDetails(
            @Path("series_id") int series_id,
            @Query("language") String language
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/videos")
    Call<FilmResource<Video>> getVideoTv(
            @Path("series_id") int series_id,
            @Query("language") String language
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/season/{season_number}")
    Call<Season> getTvSeasonsDetails(
            @Path("series_id") int series_id,
            @Path("season_number") int season_number,
            @Query("language") String language
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/tv/{series_id}/content_ratings")
    Call<FilmResource<TvCertification>> getTvContentRatings(
            @Path("series_id") int series_id
    );
}
