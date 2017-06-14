package com.example.user1.lightittest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1.lightittest.Model.Product;
import com.koushikdutta.ion.Ion;

public class ProductActivity extends AppCompatActivity {

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();

        mProduct = (Product) intent.getSerializableExtra(ListViewAdapter.PUT_EXTRA_PRODUCT_KEY);

        LayoutInflater inflater = this.getLayoutInflater();


        ImageView imageView = (ImageView) findViewById(R.id.singleProductImg);
        Ion.with(imageView).load(ApiClient.STATIC_URL + mProduct.getImageName());

        TextView tvProductTitle = (TextView) findViewById(R.id.singleProductTitle);
        TextView tvProductText = (TextView) findViewById(R.id.singleProductText);

        tvProductTitle.setText(mProduct.getProductTitle());
        tvProductText.setText(mProduct.getProductText());
    }
}
