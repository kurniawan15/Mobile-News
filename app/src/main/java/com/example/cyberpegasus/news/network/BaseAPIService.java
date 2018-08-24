package com.example.cyberpegasus.news.network;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.model.JWTToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BaseAPIService {
    // Fungsi ini untuk memanggil API http://http://192.168.1.204/DIAS/login.php
    @FormUrlEncoded
    @POST("authenticate")
    Call<JWTToken> loginRequest(@Field("username") String username,
                                @Field("password") String password);

    @GET("usermanagement/account-data")
    Call<String>getUser(@Header("Authorization")String athorization);

    @GET("beritamobile")
    Call<DataList> getData();

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
                           @Field("isi") String isi,
                           @Field("category") String catagory,
                           @Field("file") ArrayList<String> file
    );

    //Menggunakan Form-Data
    @Multipart
    @POST("beritamobile")
    Call<DataList> AddDataForm(@Part("lok_pengirim[lan]") Double lan_Lok_Pengirim,
                           @Part("lok_pengirim[long]") Double lng_Lok_Pengirim,
                           @Part("lok_berita[lan]") Double lan_Lok_Berita,
                           @Part("lok_berita[long]") Double lng_Lok_Berita,
                           @Part("pengirim") RequestBody pengirim,
                           @Part("judul") RequestBody judul,
                           @Part("date_berita") RequestBody dateBerita,
                           @Part("date_pengirim") RequestBody datePengirim,
                           @Part("isi") RequestBody isi,
                           @Part("category") RequestBody catagory,
                           @Part List<MultipartBody.Part> file
    );

    //@POST("beritamobile")
    //Call<DataList> AddData(@Body Data data);


    @Multipart
    @POST("beritamobile")
    Call<ResponseBody> uploadPhoto(
            @Part List<MultipartBody.Part> file
    );

}

