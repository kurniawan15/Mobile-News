package com.example.cyberpegasus.news.tokenmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;


import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;
import com.example.cyberpegasus.news.activity.DashboardActivity;
import com.example.cyberpegasus.news.activity.LoginActivity;
import com.example.cyberpegasus.news.activity.WelcomeActivity;

import java.util.Date;
import java.util.HashMap;

public class TokenManager {

    public static final String KEY_USER_NAME = "username";
    public static final String KEY_JWT_TOKEN = "jwttoken";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int privatemode = 0;
    private static final String PREF_NAME="JWTTOKEN";
    public  static final String isLogin="ISLOGIN";
    public  static final String checkExp="CHECKEXP";
    private static final String Name="NAME";



    public TokenManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void storeLogin(String username,String jwttoken){
        //Menyimpan session Login ke Shared Preference
        editor.putBoolean(isLogin,true);
        editor.putString(KEY_USER_NAME,username);
        editor.putString(KEY_JWT_TOKEN,jwttoken);
        editor.commit();
    }

    public HashMap<String,String> getDetailLogin(){
        //Mengambil Data Session User
        HashMap<String,String> map=new HashMap<>();
        map.put(KEY_USER_NAME,sharedPreferences.getString(KEY_USER_NAME,null));
        map.put(KEY_JWT_TOKEN,sharedPreferences.getString(KEY_JWT_TOKEN,null));
        return map;
    }

    public boolean checkLogin(){

        if(!this.isLogin()  )
        //Status isLogin true
        {
            if (!checkExp())
                //Belum masuk Exp Time

                {
                    return true;

                }

            }

       return false;

    }

    public Boolean isLogin(){
        //Status sedang Login atau tidaknya suatu user
        return sharedPreferences.getBoolean(isLogin ,false);

    }



    public void logout()
    {
        editor.clear();
        editor.commit();
    }

    public boolean checkExp ()
    {
        //Mengecek exp time dari Token sebagai syarat dari Logout otomatis
        try{
            JWT jwt = new JWT(KEY_JWT_TOKEN);
            sharedPreferences.getBoolean(isLogin,false);
            boolean isExpired = jwt.isExpired(10); // 10 seconds leeway
            if (isExpired){
                logout();
                return sharedPreferences.getBoolean(checkExp ,false);
            }
            else {
                tellExpire();
                return sharedPreferences.getBoolean(checkExp ,true);
            }

        }
        catch (DecodeException exception){
            //
        }
        return isLogin();

    }

    public Date tellExpire() {
        try {
            JWT jwt = new JWT(KEY_JWT_TOKEN);
            Date tellExpire = jwt.getExpiresAt();
            Toast.makeText(context, "Expires At :" + tellExpire.toString(), Toast.LENGTH_SHORT).show();
        }
        catch (DecodeException exception){

        }

    return tellExpire();
    }
}
