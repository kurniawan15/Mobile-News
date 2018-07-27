package com.example.cyberpegasus.news.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseAPIService {
    // Fungsi ini untuk memanggil API http://http://192.168.1.204/DIAS/login.php
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("username") String usernmame,
                                    @Field("password") String password);
}

