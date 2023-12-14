package com.example.apptracuuphim.listener;

import com.example.apptracuuphim.adapter.SearchCategoryAdapter;
import com.example.apptracuuphim.model.Film.Genres;

import java.util.List;

public interface SecondCollectListener {
    public void OnClickListener(SearchCategoryAdapter.ViewHolder viewHolder);
    public void OnCheckBoxListener(String title,List<Genres> stringList);
}
