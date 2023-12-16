package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.CreditListener;
import com.example.apptracuuphim.model.Credit.Credit;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    private Context context;
    private List<Credit> creditList;
    private CreditListener creditListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCastName;
        public ImageView castImage;
        public TextView tvCastRole;
        public LinearLayout cardMovie;

        public ViewHolder(View view) {
            super(view);
            tvCastName = view.findViewById(R.id.tv_cast_name);
            castImage = view.findViewById(R.id.cast_image);
            tvCastRole = view.findViewById(R.id.tv_cast_role);
            cardMovie = view.findViewById(R.id.card_movie);
        }
    }

    public PeopleAdapter(Context context, List<Credit> creditList, CreditListener creditListener) {
        this.context = context;
        this.creditList = creditList;
        this.creditListener = creditListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cast, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Credit credit = creditList.get(position);
        if (credit == null) {
            return;
        }
        viewHolder.tvCastName.setText(credit.getName());
        if (credit.getProfile_path() != null) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + credit.getProfile_path()).into(viewHolder.castImage);
        }
        viewHolder.tvCastRole.setText(credit.getCharacter());
        viewHolder.cardMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditListener.onClick(credit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return creditList.size();
    }
}
