package com.example.user1.lightittest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.user1.lightittest.Model.LoginRequest;
import com.example.user1.lightittest.Model.Review;
import com.example.user1.lightittest.Model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "====================";

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private EditText editTextUsername;

    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(MainActivity.MY_SHARED_PREFERENCE, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editTextUsername = (EditText) findViewById(R.id.editUsername);
        editTextPassword = (EditText) findViewById(R.id.editUserPassword);
    }

    public void onclickSignUp(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest(editTextUsername.getText().toString(),
                editTextPassword.getText().toString());

        Call<Token> call = apiService.register(loginRequest);
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
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }

    public void onclickLogin(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest("user150617", "12309856");

        Call<Token> call = apiService.login(loginRequest);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull final Response<Token> response) {
                Log.d(TAG, "login1");
                Log.d(TAG, "afsdaf "  + response.body().isSuccess());
                if (response.body().isSuccess()){
                    Log.d(TAG, "login2");
                    editor.putString(MainActivity.SHARED_TOKEN_KEY, response.body().getToken());
                    Log.d(TAG, response.body().getToken());
                    Log.d(TAG, String.valueOf(response.body().isSuccess()));
                    editor.commit();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }
}
