package com.example.apisexample.ui.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.apisexample.R;
import com.example.apisexample.data.local.FavoriteEntity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    private final List<FavoriteEntity> favorites;
    private final OnFavoriteClickListener listener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(FavoriteEntity favorite);
    }

    public FavoriteAdapter(List<FavoriteEntity> favorites, OnFavoriteClickListener listener) {
        this.favorites = favorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        FavoriteEntity fav = favorites.get(position);
        holder.title.setText(fav.getTitle());
        holder.content.setText(fav.getContent());

        if (fav.getImage() != null && !fav.getImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(fav.getImage())
                    .placeholder(R.drawable.image)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.image);
        }

        holder.itemView.setOnClickListener(v -> listener.onFavoriteClick(fav));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView content;
        final ImageView image;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            content = itemView.findViewById(R.id.textViewContent);
            image = itemView.findViewById(R.id.imageViewItem);
        }
    }
}