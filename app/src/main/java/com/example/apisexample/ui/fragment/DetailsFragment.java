package com.example.apisexample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.apisexample.R;

public class DetailsFragment extends Fragment {

    private TextView textTitle, textAuthors, textMeta, textDescription, textCategories, textISBN;
    private ImageView imageViewCover;
    private Button buttonPreview, buttonInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize UI components
        textTitle = view.findViewById(R.id.textTitle);
        textAuthors = view.findViewById(R.id.textAuthors);
        textMeta = view.findViewById(R.id.textMeta);
        textDescription = view.findViewById(R.id.textDescription);
        textCategories = view.findViewById(R.id.textCategories);
        textISBN = view.findViewById(R.id.textISBN);
        imageViewCover = view.findViewById(R.id.imageViewCover);
        buttonPreview = view.findViewById(R.id.buttonPreview);
        buttonInfo = view.findViewById(R.id.buttonInfo);

        // Retrieve data from Bundle
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title");
            String author = args.getString("author");
            String description = args.getString("description");
            String image = args.getString("image");
            String categories = args.getString("categories");
            String isbn = args.getString("isbn");

            // Set data to views
            textTitle.setText(title);
            textAuthors.setText(author);
            textDescription.setText(description);
            textCategories.setText(categories);
            textISBN.setText(isbn);

            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.image)
                    .into(imageViewCover);

        }

        // You can handle the Preview and More Info buttons as needed

        return view;
    }
}