package com.example.user1.lightittest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String STATIC_URL = "http://smktesting.herokuapp.com/static/";
    private static final String BASE_URL = "http://smktesting.herokuapp.com/";
    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
