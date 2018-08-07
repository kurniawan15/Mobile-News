package com.example.cyberpegasus.news.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.241.:9099/api/";

    private static Retrofit getRetrofitInstance(){
        Gson gson=new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static BaseAPIService getbaseAPIService(){
        return getRetrofitInstance().create(BaseAPIService.class);
    }
}
