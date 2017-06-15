package com.example.user1.lightittest.Model;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("token")
    private String token;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
