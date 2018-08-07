package com.example.cyberpegasus.news.network;

import com.example.cyberpegasus.news.model.JWTToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BaseAPIService {
    // Fungsi ini untuk memanggil API http://http://192.168.1.204/DIAS/login.php
    @FormUrlEncoded
    @POST("authenticate")
    Call<JWTToken> loginRequest(@Field("username") String username,
                                @Field("password") String password);

    @GET("usermanagement/account-data")
    Call<String>getUser(@Header("Authorization")String athorization);
}

