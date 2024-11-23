package com.example.app1;

import com.google.gson.annotations.SerializedName;

public class ProductResult {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("image")
    String productImage;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProductImage() {
        return productImage;
    }

}