package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.listener.CompanyListener;
import com.example.apptracuuphim.listener.NetworkListener;
import com.example.apptracuuphim.model.Company.Company;
import com.example.apptracuuphim.model.Network.Network;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    private Context context;
    private List<Company> companyList;
    private CompanyListener companyListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivOrigin;
        public Button btnHomepage;
        public ViewHolder(View view) {
            super(view);
            ivOrigin = view.findViewById(R.id.iv_origin);
        }
    }

    public CompanyAdapter(Context context, List<Company> companyList, CompanyListener companyListener) {
        this.context = context;
        this.companyList = companyList;
        this.companyListener = companyListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_film_logo_path, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Company company = companyList.get(position);
        if (company == null) {
            return;
        }
        if (company.getLogo_path() == null) {
            return;
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + company.getLogo_path()).into(viewHolder.ivOrigin);
        viewHolder.ivOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyListener.onClick(company);
            }
        });
    }


    @Override
    public int getItemCount() {
        return companyList.size();
    }
}
