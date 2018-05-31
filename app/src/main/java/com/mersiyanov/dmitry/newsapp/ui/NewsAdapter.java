package com.mersiyanov.dmitry.newsapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.pojo.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    private List<NewsItem> items;
    private OnNewsClickListener clickListener;

    public NewsAdapter(OnNewsClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        VH viewHolder = new VH(layoutInflater.inflate(R.layout.news_item_view, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final NewsItem item = items.get(position);

        Picasso.get().load(item.getImg()).into(holder.img);
        holder.title.setText(item.getTitle());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) {
                    clickListener.onClick(item);
                }
             }
        });
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

    static class VH extends RecyclerView.ViewHolder{

        private final TextView title;
        private final ImageView img;

        public VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            img = itemView.findViewById(R.id.news_img);
        }

    }

    public interface OnNewsClickListener {
        void onClick(NewsItem item);
    }

}
