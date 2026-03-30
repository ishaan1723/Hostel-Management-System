package com.example.hostelapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL).baseUrl("http://10.116.121.121:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}