package com.example.apptracuuphim.api;

import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.ImageType;
import com.example.apptracuuphim.model.Network.Network;
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

public interface NetworkApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    NetworkApi network = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NetworkApi.class);


    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8"
    })
    @GET("3/network/{network_id}")
    Call<Network> getNetworkDetail(
            @Path("network_id") int network_id
    );
}
