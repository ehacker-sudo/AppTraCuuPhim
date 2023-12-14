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
import com.example.apptracuuphim.listener.NetworkListener;
import com.example.apptracuuphim.listener.OriginListener;
import com.example.apptracuuphim.model.Film.Origin;
import com.example.apptracuuphim.model.Network.Network;

import java.util.List;

public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.ViewHolder> {
    private Context context;
    private List<Network> networkList;
    private NetworkListener networkListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivOrigin;
        public ViewHolder(View view) {
            super(view);
            ivOrigin = view.findViewById(R.id.iv_origin);
        }
    }

    public NetworkAdapter(Context context, List<Network> networkList, NetworkListener networkListener) {
        this.context = context;
        this.networkList = networkList;
        this.networkListener = networkListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_film_logo_path, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Network network = networkList.get(position);
        if (network == null) {
            return;
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + network.getLogo_path()).into(viewHolder.ivOrigin);
        viewHolder.ivOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkListener.onClick(network);
            }
        });
    }


    @Override
    public int getItemCount() {
        return networkList.size();
    }
}
