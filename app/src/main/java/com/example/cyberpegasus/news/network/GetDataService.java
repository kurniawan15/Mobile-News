package com.example.cyberpegasus.news.network;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */


import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {
    @GET("beritamobile")
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

    @Multipart
    @POST("file")
    Call<UploadResponse> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

}


