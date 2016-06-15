package com.example.sadhika.duckduckgo;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DuckDuckGoApplication extends Application {

    public static final String BASE_URL = "http://api.duckduckgo.com";
    private Retrofit mRetrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();

    }

    private void initRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }
}
