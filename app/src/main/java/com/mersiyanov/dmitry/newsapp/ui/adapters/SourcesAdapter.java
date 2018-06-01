package com.mersiyanov.dmitry.newsapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.pojo.SourceItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.VH> {

    private List<SourceItem> items;
    private OnSourceClickListener clickListener;

    public SourcesAdapter(OnSourceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final VH viewHolder = new VH(layoutInflater.inflate(R.layout.source_item_view, parent, false));

        viewHolder.subscibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) {
                    int pos = viewHolder.getAdapterPosition();
                    if(pos != NO_POSITION) {
                        clickListener.onClick(items.get(pos));
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final SourceItem item = items.get(holder.getAdapterPosition());
        Picasso.get().load("http://img.anews.com/media/" + item.getImg())
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .resize(400, 400).centerInside()
                .into(holder.img);
        holder.title.setText(item.getTitle());

    }

    @Override
    public int getItemCount() {
        if(items == null) return 0;
        return items.size();
    }

    public void setItems(List<SourceItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder{

        private final TextView title;
        private final ImageView img;
        private final TextView subscibe;

        public VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.source_title);
            img = itemView.findViewById(R.id.source_img);
            subscibe = itemView.findViewById(R.id.source_subscribe);
        }
    }

    public interface OnSourceClickListener {
        void onClick(SourceItem item);
    }
}
