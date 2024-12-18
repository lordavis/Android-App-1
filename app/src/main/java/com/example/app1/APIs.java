package com.example.app1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIs {
    static String BASE_URL = "https://fakestoreapi.com/";

    @GET("products")
    Call<List<ProductResult>> getProducts();
}