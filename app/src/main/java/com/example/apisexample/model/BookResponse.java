package com.example.apisexample.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {
    @SerializedName("items")
    private List<BookItem> items;

    public List<BookItem> getItems() {
        return items;
    }
}