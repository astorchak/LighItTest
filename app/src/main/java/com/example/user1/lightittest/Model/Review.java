package com.example.user1.lightittest.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class Review {

    @SerializedName("id")
    private int id;

    @SerializedName("rate")
    private int rate;

    @SerializedName("product")
    private int productId;

    @SerializedName("text")
    private String text;

    @SerializedName("created_by")
    private User user;

    @SerializedName("created_at")
    private String reviewDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReviewDateTime() {
        return reviewDateTime;
    }

    public void setReviewDateTime(String reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }
}
