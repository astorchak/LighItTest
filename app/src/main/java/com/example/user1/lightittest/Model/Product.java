package com.example.user1.lightittest.Model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private int productId;
    @SerializedName("title")
    private String productTitle;
    @SerializedName("img")
    private String productImageName;
    @SerializedName("text")
    private String productText;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getImageName() {
        return productImageName;
    }

    public void setImageName(String imageName) {
        this.productImageName = imageName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }
}
