package com.example.apptracuuphim.model.Film;

import java.util.List;

public class Film{
    protected boolean adult;
    protected String backdrop_path;
//    protected int[] genre_ids;
    protected int id;
    protected String original_language;
    protected String overview;
//    protected Double popularity;
    protected String poster_path;
    protected String release_date;
    protected double vote_average;
//    public long vote_count;
    protected String media_type;
    protected String title;
    protected String name;
    protected List<Genres> genres;
//    protected String original_name;
//    protected String original_title;
    protected String first_air_date;
//    protected String origin_country;
//    protected boolean video;
    protected String profile_path;
    protected int rate_value;

    public int getRate_value() {
        return rate_value;
    }

    public void setRate_value(int rate_value) {
        this.rate_value = rate_value;
    }

    public Film(int id, String name, String poster_path, String media_type) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.media_type = media_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
