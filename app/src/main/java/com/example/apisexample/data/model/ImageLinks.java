package com.example.apisexample.data.model;

import com.google.gson.annotations.SerializedName;

public class ImageLinks {
    @SerializedName("thumbnail")
    private String thumbnail;
    public String getThumbnail() {
        return thumbnail != null ? thumbnail : "https://i.pinimg.com/736x/f7/2f/8f/f72f8f4b18252570762a61a0345f4a17.jpg";
    }
}
