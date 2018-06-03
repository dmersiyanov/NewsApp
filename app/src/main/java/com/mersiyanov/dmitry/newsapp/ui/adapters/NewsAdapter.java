package com.mersiyanov.dmitry.newsapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    private List<NewsItem> items;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new VH(layoutInflater.inflate(R.layout.news_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final NewsItem item = items.get(holder.getAdapterPosition());
        Picasso.get().load(item.getImg())
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .resize(500, 250)
                .centerCrop()
                .into(holder.img);
        holder.title.setText(item.getTitle());

    }

    @Override
    public int getItemCount() {
        if(items == null) return 0;
        return items.size();
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<NewsItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder{

        private final TextView title;
        private final ImageView img;

        public VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            img = itemView.findViewById(R.id.news_img);
        }
    }
}
