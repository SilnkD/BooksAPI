package com.example.apisexample.data.model;

import com.google.gson.annotations.SerializedName;

public class IndustryIdentifier {

    @SerializedName("type")
    private String type;

    @SerializedName("identifier")
    private String identifier;

    public String getType() {
        return type != null ? type : "Unknown";
    }

    public String getIdentifier() {
        return identifier != null ? identifier : "No identifier";
    }
}