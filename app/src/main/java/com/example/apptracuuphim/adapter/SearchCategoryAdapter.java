package com.example.apptracuuphim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.search.ItemSearchAdapter;
import com.example.apptracuuphim.adapter.search.MediaSearchAdapter;
import com.example.apptracuuphim.adapter.search.RunTimeSearchAdapter;
import com.example.apptracuuphim.adapter.search.VoteCountSearchAdapter;
import com.example.apptracuuphim.adapter.search.VotePointSearchAdapter;
import com.example.apptracuuphim.listener.SecondCollectListener;
import com.example.apptracuuphim.model.Search.Item;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder>{
        private Context context;
        private List<Item> collection;
        private SecondCollectListener collectListener;
        private String type;
        private boolean isVisible;

    public SearchCategoryAdapter(Context context,String type, List<Item> collection, SecondCollectListener collectListener) {
        this.context = context;
        this.type = type;
        this.collection = collection;
        this.collectListener = collectListener;
    }

    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_collect, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item item = collection.get(position);
            if (item == null) {
                return;
            }
            holder.tvTitle.setText(item.getNameGroup());
            holder.tvDesc.setVisibility(View.GONE);
            holder.arrowUp.setVisibility(View.GONE);
            isVisible = false;

            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collectListener.OnClickListener(holder);
                }
            });
            holder.recycleViewItem.setLayoutManager(
                    new LinearLayoutManager(context)
            );
            if (item.getNameGroup().equals("Kiểu phim")) {
                holder.recycleViewItem.setAdapter(
                        new MediaSearchAdapter(context,type,item.getGenresList(),item.getNameGroup(),collectListener)
                );
            } else if (item.getNameGroup().equals("Thời lượng")) {
                holder.recycleViewItem.setAdapter(
                        new RunTimeSearchAdapter(context,type,item.getGenresList(),item.getNameGroup(),collectListener)
                );
            } else if (item.getNameGroup().equals("Điểm số")) {
                holder.recycleViewItem.setAdapter(
                        new VotePointSearchAdapter(context,type,item.getGenresList(),item.getNameGroup(),collectListener)
                );
            } else if (item.getNameGroup().equals("Lượng đánh giá")) {
                holder.recycleViewItem.setAdapter(
                        new VoteCountSearchAdapter(context,type,item.getGenresList(),item.getNameGroup(),collectListener)
                );
            } else if (item.getNameGroup().equals("Thể loại")) {
                holder.recycleViewItem.setAdapter(
                        new ItemSearchAdapter(context,type,item.getGenresList(),item.getNameGroup(),collectListener)
                );
            }

        }

        @Override
        public int getItemCount() {
            if (collection != null) {
                return collection.size();
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle;
            public LinearLayout tvDesc;
            public ImageView arrowUp;
            public ImageView arrowDown;
            public RecyclerView recycleViewItem;


            public ViewHolder(View view) {
                super(view);
                tvTitle = view.findViewById(R.id.tv_title);
                tvDesc = view.findViewById(R.id.tv_desc);
                recycleViewItem = view.findViewById(R.id.recycle_view_item);
                arrowDown = view.findViewById(R.id.arrow_down);
                arrowUp = view.findViewById(R.id.arrow_up);
            }
        }

    }
