package com.example.apptracuuphim.resource;

import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Movie.Movie;

import java.util.List;

public class FilmCreditResource {
    private long id;
    private List<Film> cast;
    private List<Film> crew;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Film> getCast() {
        return cast;
    }

    public void setCast(List<Film> cast) {
        this.cast = cast;
    }

    public List<Film> getCrew() {
        return crew;
    }

    public void setCrew(List<Film> crew) {
        this.crew = crew;
    }
}
