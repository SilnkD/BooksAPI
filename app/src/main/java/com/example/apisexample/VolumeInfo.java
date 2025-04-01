package com.example.apisexample;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeInfo {
    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private List<String> authors;

    @SerializedName("imageLinks")
    private ImageLinks imageLinks;

    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public ImageLinks getImageLinks() { return imageLinks; }
}
