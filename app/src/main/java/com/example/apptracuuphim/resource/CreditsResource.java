package com.example.apptracuuphim.resource;

import com.example.apptracuuphim.model.Credit.Cast;
import com.example.apptracuuphim.model.Credit.Credit;
import com.example.apptracuuphim.model.Credit.Crew;

import java.util.List;

public class CreditsResource {
    private List<Credit> cast;
    private List<Credit> crew;
    private long id;

    public List<Credit> getCast() {
        return cast;
    }

    public void setCast(List<Credit> cast) {
        this.cast = cast;
    }

    public List<Credit> getCrew() {
        return crew;
    }

    public void setCrew(List<Credit> crew) {
        this.crew = crew;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
