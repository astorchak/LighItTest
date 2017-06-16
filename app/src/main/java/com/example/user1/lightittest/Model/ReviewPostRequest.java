package com.example.user1.lightittest.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewPostRequest {

    @SerializedName("rate")
    private int rate;

    @SerializedName("text")
    private String text;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("created_by")
    private User user;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
