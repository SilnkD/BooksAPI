package com.example.apisexample.ui.booklist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apisexample.R;
import com.example.apisexample.model.Item;
import com.example.apisexample.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements Adapter.OnItemClickListener {
    private BookViewModel viewModel;
    private Adapter adapter;
    private List<Item> items = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        adapter = new Adapter(items, this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupFavoritesButton(view);
        setupSearchView(view);
        observeViewModel();

        viewModel.searchBooks("best sellers"); // дефолтный запрос
        return view;
    }

    private void setupFavoritesButton(View view) {
        ImageButton buttonFavorite = view.findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_listFragment_to_favoriteFragment);
        });
    }

    private void setupSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    viewModel.searchBooks(query.trim());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    viewModel.searchBooks(newText.trim());
                }
                return true;
            }
        });
    }

    private void observeViewModel() {
        viewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            Log.d("ListFragment", "Получено книг: " + (books != null ? books.size() : 0));
            if (books != null) {
                items.clear();
                items.addAll(books);
                adapter.updateItems(books);
            }
        });
    }

    @Override
    public void onItemClick(Item book) {
        viewModel.selectBook(book);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_listFragment_to_detailsFragment);
    }
}