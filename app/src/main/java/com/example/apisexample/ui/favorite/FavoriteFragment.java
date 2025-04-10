package com.example.apisexample.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apisexample.R;
import com.example.apisexample.data.local.FavoriteEntity;
import com.example.apisexample.model.Item;
import com.example.apisexample.viewmodel.BookViewModel;
import com.example.apisexample.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel viewModel;
    private BookViewModel bookViewModel;
    private FavoriteAdapter adapter;
    private List<FavoriteEntity> favorites = new ArrayList<>();
    private List<FavoriteEntity> fullFavoriteList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        TextView emptyText = view.findViewById(R.id.textViewEmptyFavorites);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        SearchView searchView = view.findViewById(R.id.searchView);

        adapter = new FavoriteAdapter(favorites, favorite -> {
            Item item = convertToItem(favorite);
            bookViewModel.selectBook(item);
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_favoriteFragment_to_detailsFragment);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getFavorites().observe(getViewLifecycleOwner(), favList -> {
            favorites.clear();
            fullFavoriteList.clear();
            if (favList != null && !favList.isEmpty()) {
                favorites.addAll(favList);
                fullFavoriteList.addAll(favList);
                emptyText.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        });

        setupFavoritesButton(view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterFavorites(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFavorites(newText);
                return true;
            }
        });

        return view;
    }

    private void setupFavoritesButton(View view) {
        ImageButton buttonFavorite = view.findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_favoriteFragment_to_listFragment);
        });
    }
    private void filterFavorites(String query) {
        favorites.clear();
        if (query.isEmpty()) {
            favorites.addAll(fullFavoriteList);
        } else {
            String lower = query.toLowerCase();
            for (FavoriteEntity entity : fullFavoriteList) {
                if ((entity.getTitle() != null && entity.getTitle().toLowerCase().contains(lower)) ||
                        (entity.getContent() != null && entity.getContent().toLowerCase().contains(lower))) {
                    favorites.add(entity);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private Item convertToItem(FavoriteEntity entity) {
        Item item = new Item();
        item.setId(entity.getId());
        item.setTitle(entity.getTitle());
        item.setContent(entity.getContent());
        item.setImage(entity.getImage());
        item.setDescription(entity.getDescription());
        item.setCategories(Arrays.asList(entity.getCategories().split(",\\s*")));
        item.setIsbn(entity.getIsbn());
        item.setPublisher(entity.getPublisher());
        item.setPublishedDate(entity.getPublishedDate());
        item.setPageCount(entity.getPageCount());
        return item;
    }
}