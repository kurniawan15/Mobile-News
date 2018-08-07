package com.example.cyberpegasus.news.network;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */


import com.example.cyberpegasus.news.model.DataList;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {
    @GET("rawpesans")
    Call<DataList> getData();


//    @POST("rawpesans")
//    Call<DataList> AddData(@Field("dari") String dari,
//                           @Field("type") String type,
//                           @Field("date") Date date,
//                           @Field("category") String category,
//                           @Field("pesan") String pesan
//                           );

    @FormUrlEncoded
    @POST("beritamobile")
    Call<DataList> AddData(@Field("lok_pengirim[lan]") Double lan_Lok_Pengirim,
                           @Field("lok_pengirim[long]") Double lng_Lok_Pengirim,
                           @Field("lok_berita[lan]") Double lan_Lok_Berita,
                           @Field("lok_berita[long]") Double lng_Lok_Berita,
                           @Field("pengirim") String pengirim,
                           @Field("judul") String judul,
                           @Field("date_berita") Date dateBerita,
                           @Field("date_pengirim") Date datePengirim,
                           @Field("category") String catagory,
                           @Field("file") ArrayList<String> file
                           );

    //@POST("beritamobile")
    //Call<DataList> AddData(@Body Data data);


    @Multipart
    @POST("file")
    Call<UploadResponse> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

}


