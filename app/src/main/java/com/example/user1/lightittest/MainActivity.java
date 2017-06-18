package com.example.user1.lightittest;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.widget.ListView;

import com.example.user1.lightittest.Model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    public static final String SHARED_TOKEN_KEY = "shared_token_key";

    public static final String MY_SHARED_PREFERENCE = "mySharedPreference";

    public static final String TAG = "====================";

    private ListViewAdapter listViewAdapter;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    @BindView(R.id.lvProducts) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        ListView listView = (ListView) findViewById(R.id.lvProducts);

        listViewAdapter = new ListViewAdapter(MainActivity.this, null);

        listView.setAdapter(listViewAdapter);

        sharedPreferences = getSharedPreferences(MY_SHARED_PREFERENCE, MODE_PRIVATE);

        getProducts();

    }

    public void getProducts() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull final Response<List<Product>> response) {
                Log.d(TAG, "afdsfdsfasdf");
                List<Product> list = response.body();
                listViewAdapter.addProducts(list);

//                listView.setAdapter(listViewAdapter);

                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }

    @Override
    protected void onDestroy() {
        editor = sharedPreferences.edit();
        Log.d(TAG, "CLEAR");
        Log.d(TAG, sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, ProductActivity.EMPTY_TOKEN));
//        editor.remove(SHARED_TOKEN_KEY);
        editor.clear();
        editor.commit();
        Log.d(TAG, "CLEAR");
        Log.d(TAG, sharedPreferences.getString(MainActivity.SHARED_TOKEN_KEY, ProductActivity.EMPTY_TOKEN));
        super.onDestroy();
        Log.d(TAG, "ondestroy");
    }
}

