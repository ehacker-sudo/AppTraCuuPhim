package com.example.apptracuuphim.model.Film;

import java.util.List;

public class Keywords {
    private int id;
    private List<Genres> keywords;
    private List<Genres> results;

    public List<Genres> getResults() {
        return results;
    }

    public void setResults(List<Genres> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Genres> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Genres> keywords) {
        this.keywords = keywords;
    }
}
