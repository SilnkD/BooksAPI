package com.example.apisexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> implements Filterable {

    private List<Item> items;
    private List<Item> filteredItems;
    private final OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public Adapter(List<Item> items, OnItemClickListener listener) {
        this.items = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.itemClickListener = listener;
    }

    public void updateItems(List<Item> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        this.filteredItems.clear();
        this.filteredItems.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = filteredItems.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.contentTextView.setText(item.getContent());

        if (item.getImage() != null && !item.getImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getImage())
                    .placeholder(R.drawable.image)
                    .into(holder.itemImageView);
        }

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        final TextView titleTextView;
        final TextView contentTextView;
        final ImageView itemImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            contentTextView = itemView.findViewById(R.id.textViewContent);
            itemImageView = itemView.findViewById(R.id.imageViewItem);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Item> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(items);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Item item : items) {
                        if (item.getTitle().toLowerCase().contains(filterPattern) ||
                                item.getContent().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems.clear();
                filteredItems.addAll((List<Item>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public void sortByTitle(boolean ascending) {
        Collections.sort(filteredItems, ascending ?
                Comparator.comparing(Item::getTitle) :
                (i1, i2) -> i2.getTitle().compareToIgnoreCase(i1.getTitle()));
        notifyDataSetChanged();
    }
}