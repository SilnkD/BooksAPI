package com.example.apisexample.ui.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;

import com.example.apisexample.R;
import com.example.apisexample.ui.adapter.Adapter;
import com.example.apisexample.data.model.Item;
import com.example.apisexample.viewmodel.BookViewModel;
import com.example.apisexample.data.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements Adapter.OnItemClickListener {
    private BookViewModel viewModel;
    private Adapter adapter;
    private List<Item> items = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BookRepository repository = new BookRepository();
        BookViewModel.Factory factory = new BookViewModel.Factory(repository);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(BookViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        adapter = new Adapter(items, this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupSearch();
        observeViewModel();
        return view;
    }

    private void setupSearch() {
        viewModel.searchBooks("best sellers");
    }

    private void observeViewModel() {
        viewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            items.clear();
            if (books != null) {
                items.addAll(books);
            }
            adapter.updateItems(items);
        });
    }

    @Override
    public void onItemClick(Item book) {
        viewModel.selectBook(book);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_listFragment_to_detailsFragment);
    }
}
