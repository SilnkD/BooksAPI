package com.example.apisexample.ui.bookdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.apisexample.R;
import com.example.apisexample.model.Item;
import com.example.apisexample.data.local.FavoriteEntity;
import com.example.apisexample.viewmodel.BookViewModel;
import com.example.apisexample.viewmodel.FavoriteViewModel;

public class DetailsFragment extends Fragment {

    private TextView textTitle, textAuthors, textPublisher, textPublishedDate, textDescription, textCategories, textISBN, textPageCount;
    private ImageView imageViewCover;
    private ImageButton buttonFavorite;

    private BookViewModel bookViewModel;
    private FavoriteViewModel favoriteViewModel;

    private boolean isFavorite = false;
    private Item currentBook;
    private FavoriteEntity currentFavoriteEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        initViews(view);

        bookViewModel.getSelectedBook().observe(getViewLifecycleOwner(), book -> {
            if (book != null) {
                currentBook = book;
                checkIfFavorite(book.getTitle());
                updateUI(book);
            }
        });

        buttonFavorite.setOnClickListener(v -> {
            if (currentBook == null) return;

            if (isFavorite && currentFavoriteEntity != null) {
                favoriteViewModel.removeFromFavorites(currentFavoriteEntity);
                buttonFavorite.setImageResource(R.drawable.ic_star_outline);
            } else {
                favoriteViewModel.addToFavorites(convertToEntity(currentBook));
                buttonFavorite.setImageResource(R.drawable.ic_star_filled);
            }
            isFavorite = !isFavorite;
        });

        return view;
    }

    private void initViews(View view) {
        textTitle = view.findViewById(R.id.textTitle);
        textAuthors = view.findViewById(R.id.textAuthors);
        textPublisher = view.findViewById(R.id.textPublisher);
        textPublishedDate = view.findViewById(R.id.textPublishedDate);
        textDescription = view.findViewById(R.id.textDescription);
        textCategories = view.findViewById(R.id.textCategories);
        textISBN = view.findViewById(R.id.textISBN);
        textPageCount = view.findViewById(R.id.textPageCount);
        imageViewCover = view.findViewById(R.id.imageViewCover);
        buttonFavorite = view.findViewById(R.id.buttonFavorite);
    }

    @SuppressLint("DefaultLocale")
    private void updateUI(Item book) {
        textTitle.setText(book.getTitle());
        textAuthors.setText(book.getContent());
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

    private void checkIfFavorite(String title) {
        favoriteViewModel.getFavoriteByTitle(title).observe(getViewLifecycleOwner(), result -> {
            isFavorite = result != null;
            currentFavoriteEntity = result;
            buttonFavorite.setImageResource(isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star_outline);
        });
    }

    private FavoriteEntity convertToEntity(Item item) {
        return new FavoriteEntity(
                item.getTitle(),
                item.getContent(),
                item.getImage(),
                item.getDescription(),
                String.join(", ", item.getCategories()),
                item.getIsbn(),
                item.getPublisher(),
                item.getPublishedDate(),
                item.getPageCount()
        );
    }
}