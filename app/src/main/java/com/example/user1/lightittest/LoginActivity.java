package com.example.user1.lightittest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user1.lightittest.Model.LoginRequest;
import com.example.user1.lightittest.Model.Token;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "====================";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    boolean isInternetConnected;

    @BindView(R.id.editUsername) EditText editTextUsername;
    @BindView(R.id.editUserPassword) EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(MainActivity.MY_SHARED_PREFERENCE, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void onclickSignUp(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest(editTextUsername.getText().toString(),
                editTextPassword.getText().toString());

        Call<Token> call = apiService.register(loginRequest);
        if (isInternetConnected){
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(@NonNull Call<Token> call, @NonNull final Response<Token> response) {
                    if (response.body().isSuccess()){
                        editor.putString(MainActivity.SHARED_TOKEN_KEY, response.body().getToken());
                        editor.commit();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, MainActivity.SERVER_ERROR_RESPONSE, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(LoginActivity.this, MainActivity.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }

    public void onclickLogin(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest(editTextUsername.getText().toString(),
                editTextPassword.getText().toString());

        Call<Token> call = apiService.login(loginRequest);
        if (isInternetConnected){
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(@NonNull Call<Token> call, @NonNull final Response<Token> response) {
                    if (response.body().isSuccess()){
                        editor.putString(MainActivity.SHARED_TOKEN_KEY, response.body().getToken());
                        editor.commit();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, MainActivity.SERVER_ERROR_RESPONSE, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(LoginActivity.this, MainActivity.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isInternetConnected = NetworkManager.isInternetAvailable(context);
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
