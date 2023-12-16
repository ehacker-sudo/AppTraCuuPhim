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
import com.example.apptracuuphim.listener.CastListener;
import com.example.apptracuuphim.listener.CreditListener;
import com.example.apptracuuphim.model.Credit.Cast;
import com.example.apptracuuphim.model.Credit.Credit;
import com.example.apptracuuphim.model.Credit.Crew;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    private Context context;
    private List<Credit> creditList;
    private CreditListener creditListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView imgCredit;
        public TextView tvInfo;
        public LinearLayout itemCredit;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            imgCredit = view.findViewById(R.id.img_credit);
            tvInfo = view.findViewById(R.id.tv_info);
            itemCredit = view.findViewById(R.id.item_credit);
        }
    }

    public CreditAdapter(Context context, List<Credit> creditList, CreditListener creditListener) {
        this.context = context;
        this.creditList = creditList;
        this.creditListener = creditListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_credit, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Credit credit = creditList.get(position);
        if (credit == null) {
            return;
        }
        viewHolder.tvName.setText(credit.getName());
        if (credit.getProfile_path() != null) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + credit.getProfile_path()).into(viewHolder.imgCredit);
        }
        viewHolder.tvInfo.setText(credit.getCharacter());
        viewHolder.itemCredit.setOnClickListener(new View.OnClickListener() {
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
