package com.example.apisexample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.apisexample.R;
import com.example.apisexample.data.model.Item;
import com.example.apisexample.viewmodel.BookViewModel;

public class DetailsFragment extends Fragment {

    private TextView textTitle, textAuthors, textMeta, textDescription, textCategories, textISBN;
    private ImageView imageViewCover;
    private Button buttonPreview, buttonInfo;
    private BookViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);  // Получаем Shared ViewModel
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        textTitle = view.findViewById(R.id.textTitle);
        textAuthors = view.findViewById(R.id.textAuthors);
        textMeta = view.findViewById(R.id.textMeta);
        textDescription = view.findViewById(R.id.textDescription);
        textCategories = view.findViewById(R.id.textCategories);
        textISBN = view.findViewById(R.id.textISBN);
        imageViewCover = view.findViewById(R.id.imageViewCover);
        buttonPreview = view.findViewById(R.id.buttonPreview);
        buttonInfo = view.findViewById(R.id.buttonInfo);

        viewModel.getSelectedBook().observe(getViewLifecycleOwner(), book -> {
            if (book != null) {
                updateUI(book);
            }
        });

        return view;
    }

    private void updateUI(Item book) {
        textTitle.setText(book.getTitle());
        textAuthors.setText(book.getContent());
        textDescription.setText(book.getDescription());
        textCategories.setText(String.format("Categories: %s", book.getCategories()));
        textISBN.setText(String.format("ISBN: %s", book.getIsbn()));

        Glide.with(this)
                .load(book.getImage())
                .placeholder(R.drawable.image)
                .into(imageViewCover);
    }
}