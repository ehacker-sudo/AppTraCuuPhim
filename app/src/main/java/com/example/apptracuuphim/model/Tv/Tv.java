package com.example.apptracuuphim.model.Tv;

import com.example.apptracuuphim.model.Company.Company;
import com.example.apptracuuphim.model.Film.Episode;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.model.Film.Origin;
import com.example.apptracuuphim.model.Network.Network;

import java.util.List;

public class Tv extends Film{
    private int number_of_episodes;
    private int[] episode_run_time;
    private Episode next_episode_to_air;
    private Episode last_episode_to_air;
    private boolean in_production;
    private List<Network> networks;
    private List<Company> production_companies;
    private int number_of_seasons;
    private List<Season> seasons;
    public int getNumber_of_episodes() {
        return number_of_episodes;
    }
    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }
    public Episode getNext_episode_to_air() {
        return next_episode_to_air;
    }
    public void setNext_episode_to_air(Episode next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<Company> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<Company> production_companies) {
        this.production_companies = production_companies;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(int[] episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public Episode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(Episode last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
}
