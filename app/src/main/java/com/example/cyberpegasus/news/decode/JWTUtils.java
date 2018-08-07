package com.example.cyberpegasus.news.decode;

import android.media.UnsupportedSchemeException;
import android.preference.PreferenceActivity;
import android.util.Base64;
import android.util.Log;

import com.example.cyberpegasus.news.model.JWTToken;
import com.example.cyberpegasus.news.network.BaseAPIService;
import com.example.cyberpegasus.news.network.RetrofitClient;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;

public class JWTUtils {

    public static void decodeJWT(String EncodeString)throws Exception
    {
        String[] splitstr = EncodeString.split("\\.");
        Log.d("", "Header"+getJson(splitstr[0]));
        Log.d("", "Payload"+getJson(splitstr[1]));
        Log.d("", "Signature"+getJson(splitstr[2]));

    }

    public static String getJson(String EncodeString) throws UnsupportedEncodingException{

        byte[] decodebyte= Base64.decode(EncodeString,Base64.URL_SAFE);
        return new String(decodebyte,"UTF-8");
    }


}
