package com.example.apptracuuphim.model.Movie;

import com.example.apptracuuphim.model.Company.Company;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.Origin;

import java.util.List;

public class Movie extends Film{
    public Film belongs_to_collection;
    public long budget;
    public Long runtime;
    public Long revenue;
    public List<Company> production_companies;

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public List<Company> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<Company> production_companies) {
        this.production_companies = production_companies;
    }

    public Film getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public void setBelongs_to_collection(Film belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }
}
