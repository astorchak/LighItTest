package com.example.user1.lightittest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.user1.lightittest.Model.Product;
import com.example.user1.lightittest.Model.Review;
import com.example.user1.lightittest.Model.ReviewPostRequest;
import com.example.user1.lightittest.Model.ReviewPostResponse;
import com.example.user1.lightittest.Model.User;
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

    private RatingBar ratingBar;

    private EditText editText;

    private ListView listView;

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

        listView = (ListView) findViewById(R.id.lvReviews);

        listViewRewiewsAdapter = new ListViewRewiewsAdapter(ProductActivity.this, null);

        listView.setAdapter(listViewRewiewsAdapter);

        getReviews();

        tableLayoutLogin = (TableLayout) findViewById(R.id.tableLayoutLogin);

        sharedPreferences = getSharedPreferences(MainActivity.MY_SHARED_PREFERENCE, MODE_PRIVATE);

        boolean b = checkToken();

        ratingBar = (RatingBar) findViewById(R.id.ratingBarReview);

        ratingBar.setRating(5);
        ratingBar.setNumStars(5);

        editText = (EditText) findViewById(R.id.editReview);

        /*made able to scroll listView inside scrollView*/
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
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
        Log.d(TAG, sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, EMPTY_TOKEN));
        return sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, EMPTY_TOKEN) != EMPTY_TOKEN;
    }

    private void showSendReview(){

    }

    public void btnSendClick(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ReviewPostRequest reviewPostRequest = new ReviewPostRequest();

        reviewPostRequest.setText(editText.getText().toString());
        reviewPostRequest.setRate((int) ratingBar.getRating());

        Call<ReviewPostResponse> call = apiService.sendReview("Token " + sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, EMPTY_TOKEN), reviewPostRequest, mProduct.getProductId());
        call.enqueue(new Callback<ReviewPostResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewPostResponse> call, @NonNull final Response<ReviewPostResponse> response) {
                Log.d(TAG, "9999999999999999");
                Log.d(TAG, String.valueOf(response.headers()));
                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG, String.valueOf(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<ReviewPostResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }

}
