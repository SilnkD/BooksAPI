package com.example.apisexample.data.model;

import com.google.gson.annotations.SerializedName;

public class BookItem {
    @SerializedName("id")
    private String id;

    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    public String getId() { return id; }
    public VolumeInfo getVolumeInfo() { return volumeInfo; }
}