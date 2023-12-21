package com.example.apptracuuphim.adapter.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.SecondCollectListener;
import com.example.apptracuuphim.model.Film.CheckBoxModel;
import com.example.apptracuuphim.model.Film.Genres;

import java.util.ArrayList;
import java.util.List;

public class MediaSearchAdapter extends RecyclerView.Adapter<MediaSearchAdapter.ViewHolder>{
    private Context context;
    private List<Genres> genresList;
    public String title;
    private SecondCollectListener secondCollectListener;
    private List<Genres> arrayList1 = new ArrayList<>();
    private Genres Radiogenres;
    private String type;
    private static CheckBoxModel checkBoxModel = new CheckBoxModel();

    public MediaSearchAdapter(Context context, String type, List<Genres> genresList, String title, SecondCollectListener secondCollectListener) {
        this.context = context;
        this.type = type;
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

        if (type == "checkbox") {
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
        } else if (type == "radio") {
            holder.tvTitle.setText(genres.getName());
            holder.tvTitle.setTag(new Integer(position));
            if(holder.tvTitle.isChecked() && position == 0)
            {
                checkBoxModel.setLastChecked(holder.tvTitle);
                checkBoxModel.setLastCheckedPos(0);
            }
            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CheckBox cb = (CheckBox)v;
                    int clickedPos = ((Integer)cb.getTag()).intValue();
                    if(cb.isChecked())
                    {
                        Radiogenres = genres;
                        if(checkBoxModel.getLastChecked() != null)
                        {
                            checkBoxModel.getLastChecked().setChecked(false);
                        }

                        checkBoxModel.setLastChecked(cb);
                        checkBoxModel.setLastCheckedPos(clickedPos);
                    }
                    else{
                        Radiogenres = new Genres(1000,"");
                        checkBoxModel.setLastChecked(null);
                    }

                    secondCollectListener.OnRadioListener(title,Radiogenres);
                }
            });
        }

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
