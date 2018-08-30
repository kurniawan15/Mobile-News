package com.example.cyberpegasus.news.network;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.1.241:9095/api/";

    private RetrofitInstance() {}

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

