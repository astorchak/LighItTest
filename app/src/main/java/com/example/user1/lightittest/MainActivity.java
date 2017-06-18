package com.example.user1.lightittest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.widget.ListView;
import android.widget.Toast;

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
    public static final String WIFI_STATE_CHANGE = "android.net.wifi.STATE_CHANGE";
    public static final String CONN_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String NO_INTERNET_CONNECTION = "No internet connection";
    public static final String SERVER_ERROR_RESPONSE = "Server response error. Please try later";

    private ListViewAdapter listViewAdapter;

    boolean isInternetConnected;

    @BindView(R.id.lvProducts) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listViewAdapter = new ListViewAdapter(MainActivity.this, null);
        listView.setAdapter(listViewAdapter);
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkManager.isInternetAvailable(context)){
                isInternetConnected = true;
                getProducts();
            } else {
                isInternetConnected = false;
            }
        }
    };

    public void getProducts() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Product>> call = apiService.getProducts();
        if (isInternetConnected){
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull final Response<List<Product>> response) {
                    List<Product> list = response.body();
                    listViewAdapter.addProducts(list);
                    listViewAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, SERVER_ERROR_RESPONSE, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(MainActivity.this, NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }

    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction(WIFI_STATE_CHANGE);
        internetFilter.addAction(CONN_CONNECTIVITY_CHANGE);
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

