package com.example.user1.lightittest.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewPostResponse {

    @SerializedName("review_id")
    private int review_id;

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }
}
