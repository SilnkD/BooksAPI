package com.example.apisexample.data.model;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String id;
    private String title;
    private String content;
    private String image;
    private boolean isFavourite;
    private String description;
    private List<String> categories;
    private String isbn;
    private String publisher;
    private String publishedDate;
    private int pageCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image.replace("http://", "https://");
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getDescription() {
        return description != null ? description : "No description";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories != null ? categories : new ArrayList<>();
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getIsbn() {
        return isbn != null ? isbn : "ISBN not available";
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher != null ? publisher : "Publisher not available";
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate != null ? publishedDate : "Published date not available";
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}