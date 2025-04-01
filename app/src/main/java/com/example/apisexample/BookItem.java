package com.example.apisexample;

import com.google.gson.annotations.SerializedName;

public class BookItem {
    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }
}
