package com.example.apisexample.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class VolumeInfo {
    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private List<String> authors;

    @SerializedName("imageLinks")
    private ImageLinks imageLinks;

    @SerializedName("description")
    private String description;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("industryIdentifiers")
    private List<IndustryIdentifier> industryIdentifiers;

    // Геттеры для всех полей

    public String getTitle() {
        return title != null ? title : "No Title";
    }

    public List<String> getAuthors() {
        return authors != null ? authors : Collections.emptyList();
    }

    public ImageLinks getImageLinks() {
        return imageLinks != null ? imageLinks : new ImageLinks();
    }

    public String getDescription() {
        return description != null ? description : "No description available";
    }

    public List<String> getCategories() {
        return categories != null ? categories : Collections.emptyList();
    }

    public List<IndustryIdentifier> getIndustryIdentifiers() {
        return industryIdentifiers != null ? industryIdentifiers : Collections.emptyList();
    }
}