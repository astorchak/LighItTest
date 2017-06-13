package com.example.user1.lightittest;

import com.example.user1.lightittest.Model.Product;
import com.example.user1.lightittest.Model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/products")
    Call<List<Product>> getProducts();

    @GET("/api/reviews/{product_id}")
    Call<List<Review>> getReviews(@Path("product_id") int productId);
}
