package com.example.apptracuuphim.api;

import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.Movie.Movie;
import com.example.apptracuuphim.model.SocialMedia.SocialMedia;
import com.example.apptracuuphim.model.Tv.Tv;
import com.example.apptracuuphim.resource.FilmCreditResource;
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

public interface FilmApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    FilmApi film = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FilmApi.class);


    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/trending/all/{time_window}")
    Call<FilmResource<Film>> getTrending(
            @Path("time_window") String time_window,
            @Query("language") String language
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/search/{search_type}")
    Call<FilmResource<Film>> getMultiSearch(
            @Path("search_type") String search_type,
            @Query("query") String query,
            @Query("language") String language,
            @Query("page") int page
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/{media_type}/{film_id}/images")
    Call<ImageType> getImages(
            @Path("media_type") String media_type,
            @Path("film_id") int film_id,
            @Query("language") String language
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/person/{person_id}/{media_type_credits}")
    Call<FilmCreditResource> getPeopleFilmCredits(
            @Path("media_type_credits") String media_type_credits,
            @Path("person_id") int person_id,
            @Query("language") String language
    );


    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/{media_type}/{film_id}/external_ids")
    Call<SocialMedia> getExternalIDs(
            @Path("media_type") String media_type,
            @Path("film_id") int film_id
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/genre/tv/list")
    Call<Tv> getGenresTvList();

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/genre/movie/list")
    Call<Movie> getGenresMovieList();

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/genre/{media_type}/list")
    Call<Film> getGenresList(
            @Path("media_type") String media_type
    );


    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/discover/{media_type}")
    Call<FilmResource<Film>> getDiscover(
            @Path("media_type") String media_type,
            @Query("language") String language,
            @Query("page") int page,
            @Query("include_adult") boolean include_adult,
            @Query("sort_by") String sort_by,
            @Query("with_genres") String with_genres,
            @Query("with_runtime.lte") int with_runtime_lte,
            @Query("with_runtime.gte") int with_runtime_gte,
            @Query("vote_average.gte") int vote_average_gte,
            @Query("vote_count.gte") int vote_count_gte

    );
}
