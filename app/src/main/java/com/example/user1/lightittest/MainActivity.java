package com.example.user1.lightittest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.widget.ListView;

import com.example.user1.lightittest.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "====================";

    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.lvProducts);

        listViewAdapter = new ListViewAdapter(MainActivity.this, null);

        listView.setAdapter(listViewAdapter);

        getProducts();

    }

    public void getProducts() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull final Response<List<Product>> response) {
                List<Product> list = response.body();
                listViewAdapter.addProducts(list);
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }
}

