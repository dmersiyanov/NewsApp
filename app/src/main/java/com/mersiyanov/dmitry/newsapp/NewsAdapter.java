package com.mersiyanov.dmitry.newsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    private List items;
    private OnNewsClickListener clickListener;

    public NewsAdapter(List items, OnNewsClickListener clickListener) {
        this.items = items;
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

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }
    }

    public interface OnNewsClickListener {
        void onClick();
    }

}
