package com.example.user1.lightittest;

import com.example.user1.lightittest.Model.LoginRequest;
import com.example.user1.lightittest.Model.Product;
import com.example.user1.lightittest.Model.Review;
import com.example.user1.lightittest.Model.ReviewPostRequest;
import com.example.user1.lightittest.Model.ReviewPostResponse;
import com.example.user1.lightittest.Model.Token;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/products")
    Call<List<Product>> getProducts();

    @GET("/api/reviews/{product_id}")
    Call<List<Review>> getReviews(@Path("product_id") int productId);

    @POST("/api/register/")
    Call<Token> register(@Body LoginRequest loginRequest);

    @POST("/api/login/")
    Call<Token> login(@Body LoginRequest loginRequest);


    @POST("/api/reviews/{product_id}")
    Call<ReviewPostResponse> sendReview(@Header("Authorization") String token, @Body ReviewPostRequest reviewPostRequest, @Path("product_id") int productId);

//    @POST("/api/reviews/")
//    Call<ReviewPostResponse> sendReview(@Header("Authorization") String token, @Body ReviewPostRequest reviewPostRequest);

}
