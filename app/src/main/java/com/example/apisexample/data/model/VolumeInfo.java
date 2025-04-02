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

    public String getTitle() {
        return title != null ? title : "No Title";
    }

    public List<String> getAuthors() {
        return authors != null ? authors : Collections.emptyList();
    }

    public ImageLinks getImageLinks() {
        return imageLinks != null ? imageLinks : new ImageLinks();
    }
}