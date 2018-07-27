package com.example.cyberpegasus.news.network;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */


import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @GET("rawpesans")
    Call<DataList> getData();

//    @FormUrlEncoded
//    @POST("rawpesans")
//    Call<DataList> AddData(@Field("dari") String dari,
//                           @Field("type") String type,
//                           @Field("date") Date date,
//                           @Field("category") String category,
//                           @Field("pesan") String pesan
//                           );

    @POST("rawpesans")
    Call<DataList> AddData(@Body Data data);

}


