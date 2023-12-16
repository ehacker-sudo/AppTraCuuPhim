package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.SecondCollectListener;
import com.example.apptracuuphim.model.Film.Genres;

import java.util.ArrayList;
import java.util.List;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ViewHolder>{
    private Context context;
    private List<Genres> genresList;
    private String title;
    private SecondCollectListener secondCollectListener;
    private List<Genres> arrayList1 = new ArrayList<>();


    public ItemSearchAdapter(Context context, List<Genres> genresList,String title,SecondCollectListener secondCollectListener) {
        this.context = context;
        this.genresList = genresList;
        this.title = title;
        this.secondCollectListener = secondCollectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_check_box, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genres genres = genresList.get(position);
        if (genres == null) {
            return;
        }
        holder.tvTitle.setText(genres.getName());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvTitle.isChecked()){
                    arrayList1.add(genres);
                } else {
                    arrayList1.remove(genres);
                }
                secondCollectListener.OnCheckBoxListener(title,arrayList1);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (genresList != null) {
            return genresList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox tvTitle;


        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_item);
        }
    }

}
