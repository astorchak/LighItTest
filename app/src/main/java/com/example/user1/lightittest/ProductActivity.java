package com.example.user1.lightittest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1.lightittest.Model.Product;
import com.example.user1.lightittest.Model.Review;
import com.example.user1.lightittest.Model.ReviewPostRequest;
import com.example.user1.lightittest.Model.ReviewPostResponse;
import com.example.user1.lightittest.Model.User;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "====================";
    public static final String EMPTY_TOKEN = "emptyToken";

    private Product mProduct;
    private ListViewRewiewsAdapter listViewRewiewsAdapter;
    private SharedPreferences sharedPreferences;
    boolean isInternetConnected;

    @BindView(R.id.singleProductImg) ImageView imageView;
    @BindView(R.id.singleProductTitle) TextView tvProductTitle;
    @BindView(R.id.singleProductText) TextView tvProductText;
    @BindView(R.id.lvReviews) ListView listView;
    @BindView(R.id.ratingBarReview) RatingBar ratingBar;
    @BindView(R.id.editReview) EditText editText;
    @BindView(R.id.tableLayoutLogin) TableLayout tableLayoutLogin;
    @BindView(R.id.layoutReviewsInput) LinearLayout layoutReviewsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mProduct = (Product) intent.getSerializableExtra(ListViewAdapter.PUT_EXTRA_PRODUCT_KEY);
        Ion.with(imageView).load(ApiClient.STATIC_URL + mProduct.getImageName());
        tvProductTitle.setText(mProduct.getProductTitle());
        tvProductText.setText(mProduct.getProductText());
        listViewRewiewsAdapter = new ListViewRewiewsAdapter(ProductActivity.this, null);
        listView.setAdapter(listViewRewiewsAdapter);
        sharedPreferences = getSharedPreferences(MainActivity.MY_SHARED_PREFERENCE, MODE_PRIVATE);
        ratingBar.setRating(5);
        ratingBar.setNumStars(5);

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
        if (isInternetConnected) {
            call.enqueue(new Callback<List<Review>>() {
                @Override
                public void onResponse(@NonNull Call<List<Review>> call, @NonNull final Response<List<Review>> response) {
                    List<Review> list = response.body();
                    listViewRewiewsAdapter.addReviews(list);
                    listViewRewiewsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<List<Review>> call, @NonNull Throwable t) {
                    Toast.makeText(ProductActivity.this, MainActivity.SERVER_ERROR_RESPONSE, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(ProductActivity.this, MainActivity.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnGetAccount(View view) {
        Intent intent = new Intent(ProductActivity.this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    private boolean checkToken(){
        Log.d(TAG, sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, EMPTY_TOKEN));
        return sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, EMPTY_TOKEN) != EMPTY_TOKEN;
    }

    private void showSendReview(){
        if (checkToken()){
            tableLayoutLogin.setVisibility(View.GONE);
            layoutReviewsInput.setVisibility(View.VISIBLE);
        }
        else {
            tableLayoutLogin.setVisibility(View.VISIBLE);
            layoutReviewsInput.setVisibility(View.GONE);
        }
    }

    public void btnSendClick(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ReviewPostRequest reviewPostRequest = new ReviewPostRequest();

        reviewPostRequest.setText(editText.getText().toString());
        reviewPostRequest.setRate((int) ratingBar.getRating());

        Call<ReviewPostResponse> call = apiService.sendReview("Token " + sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, EMPTY_TOKEN), reviewPostRequest, mProduct.getProductId());
        if (isInternetConnected){
            call.enqueue(new Callback<ReviewPostResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReviewPostResponse> call, @NonNull final Response<ReviewPostResponse> response) {

                }
                @Override
                public void onFailure(@NonNull Call<ReviewPostResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ProductActivity.this, MainActivity.SERVER_ERROR_RESPONSE, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(ProductActivity.this, MainActivity.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showSendReview();
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkManager.isInternetAvailable(context)){
                isInternetConnected = true;
                showSendReview();
                getReviews();
            } else {
                isInternetConnected = false;
            }
        }
    };

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction(MainActivity.WIFI_STATE_CHANGE);
        internetFilter.addAction(MainActivity.CONN_CONNECTIVITY_CHANGE);
        registerReceiver(broadcastReceiver, internetFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}
