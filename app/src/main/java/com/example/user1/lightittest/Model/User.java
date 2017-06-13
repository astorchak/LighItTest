package com.example.user1.lightittest.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("password")
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
