package com.example.user1.lightittest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.user1.lightittest.Model.Product;
import com.example.user1.lightittest.Model.Review;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "====================";

    private static final String EMPTY_TOKEN = "emptyToken";

    private Product mProduct;

    private ListViewRewiewsAdapter listViewRewiewsAdapter;

    TableLayout tableLayoutLogin;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();

        mProduct = (Product) intent.getSerializableExtra(ListViewAdapter.PUT_EXTRA_PRODUCT_KEY);

        ImageView imageView = (ImageView) findViewById(R.id.singleProductImg);
        Ion.with(imageView).load(ApiClient.STATIC_URL + mProduct.getImageName());

        TextView tvProductTitle = (TextView) findViewById(R.id.singleProductTitle);
        TextView tvProductText = (TextView) findViewById(R.id.singleProductText);

        tvProductTitle.setText(mProduct.getProductTitle());
        tvProductText.setText(mProduct.getProductText());

        ListView listView = (ListView) findViewById(R.id.lvReviews);

        listViewRewiewsAdapter = new ListViewRewiewsAdapter(ProductActivity.this, null);

        listView.setAdapter(listViewRewiewsAdapter);

        getReviews();

        tableLayoutLogin = (TableLayout) findViewById(R.id.tableLayoutLogin);

        sharedPreferences = getSharedPreferences(MainActivity.MY_SHARED_PREFERENCE, MODE_PRIVATE);

        boolean b = checkToken();


//        tableLayout.removeAllViews();
    }

    private void getReviews() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Review>> call = apiService.getReviews(mProduct.getProductId());
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(@NonNull Call<List<Review>> call, @NonNull final Response<List<Review>> response) {
                List<Review> list = response.body();
                listViewRewiewsAdapter.addReviews(list);
                listViewRewiewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Review>> call, @NonNull Throwable t) {
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }

    public void btnGetAccount(View view) {
        Intent intent = new Intent(ProductActivity.this, LoginActivity.class);
        /*TODO check 1*/
        startActivityForResult(intent, 1);
    }

    private boolean checkToken(){
        Log.d(TAG, sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, "NOTOKEN"));
        return sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, "NOTOKEN") != EMPTY_TOKEN;
    }

}
