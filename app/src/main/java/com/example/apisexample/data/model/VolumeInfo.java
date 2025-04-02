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

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("publishedDate")
    private String publishedDate;

    @SerializedName("pageCount")
    private int pageCount;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("industryIdentifiers")
    private List<IndustryIdentifier> industryIdentifiers;

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

    public String getPublisher() {
        return publisher != null ? publisher : "Publisher not available";
    }

    public String getPublishedDate() {
        return publishedDate != null ? publishedDate : "Published date not available";
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<String> getCategories() {
        return categories != null ? categories : Collections.emptyList();
    }

    public List<IndustryIdentifier> getIndustryIdentifiers() {
        return industryIdentifiers != null ? industryIdentifiers : Collections.emptyList();
    }
}