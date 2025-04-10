package com.example.apisexample.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.apisexample.R;
import com.example.apisexample.data.local.FavoriteEntity;
import com.example.apisexample.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel viewModel;
    private FavoriteAdapter adapter;
    private List<FavoriteEntity> favorites = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(FavoriteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        TextView emptyText = view.findViewById(R.id.textViewEmptyFavorites);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        adapter = new FavoriteAdapter(favorites, favorite -> {
            viewModel.removeFromFavorites(favorite);
            Toast.makeText(getContext(), "Удалено из избранного", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getFavorites().observe(getViewLifecycleOwner(), favList -> {
            favorites.clear();
            if (favList != null && !favList.isEmpty()) {
                favorites.addAll(favList);
                emptyText.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}