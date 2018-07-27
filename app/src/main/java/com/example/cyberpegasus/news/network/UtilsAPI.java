package com.example.cyberpegasus.news.network;

public class UtilsAPI {
    // 192.168.1.204/DIAS/login.php ini adalah localhost.
    public static final String BASE_URL_API = "192.168.1.241.:9007/api/authenticate";

    // Mendeklarasikan Interface BaseApiService
    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
