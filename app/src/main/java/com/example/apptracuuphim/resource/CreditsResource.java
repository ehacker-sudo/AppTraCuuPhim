package com.example.apptracuuphim.resource;

import com.example.apptracuuphim.model.Credit.Cast;
import com.example.apptracuuphim.model.Credit.Crew;

import java.util.List;

public class CreditsResource {
    private List<Cast> cast;
    private List<Crew> crew;
    private long id;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
