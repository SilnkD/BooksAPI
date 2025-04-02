package com.example.apisexample.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.apisexample.R;
import com.example.apisexample.data.model.Item;
import com.example.apisexample.viewmodel.BookViewModel;

public class DetailsFragment extends Fragment {

    private TextView textTitle, textAuthors, textPublisher, textPublishedDate, textDescription, textCategories, textISBN, textPageCount;
    private ImageView imageViewCover;
    private BookViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        textTitle = view.findViewById(R.id.textTitle);
        textAuthors = view.findViewById(R.id.textAuthors);
        textPublisher = view.findViewById(R.id.textPublisher);
        textPublishedDate = view.findViewById(R.id.textPublishedDate);
        textDescription = view.findViewById(R.id.textDescription);
        textCategories = view.findViewById(R.id.textCategories);
        textISBN = view.findViewById(R.id.textISBN);
        textPageCount = view.findViewById(R.id.textPageCount);
        imageViewCover = view.findViewById(R.id.imageViewCover);

        viewModel.getSelectedBook().observe(getViewLifecycleOwner(), book -> {
            if (book != null) {
                updateUI(book);
            }
        });
        return view;
    }

    @SuppressLint("DefaultLocale")
    private void updateUI(Item book) {
        textTitle.setText(book.getTitle());

        String authors = String.join(", ", book.getContent());
        textAuthors.setText(authors);

        textPublisher.setText(String.format("Publisher: %s", book.getPublisher()));
        textPublishedDate.setText(String.format("Published Date: %s", book.getPublishedDate()));
        textDescription.setText(book.getDescription());
        textCategories.setText(String.format("Categories: %s", String.join(", ", book.getCategories())));
        textISBN.setText(String.format("ISBN: %s", book.getIsbn()));
        textPageCount.setText(String.format("Page Count: %d", book.getPageCount()));


        Glide.with(this)
                .load(book.getImage())
                .placeholder(R.drawable.image)
                .into(imageViewCover);
    }
}