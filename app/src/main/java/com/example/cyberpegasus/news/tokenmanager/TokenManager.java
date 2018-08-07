package com.example.cyberpegasus.news.tokenmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.cyberpegasus.news.activity.LoginActivity;

import java.util.HashMap;

public class TokenManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int privatemode = 0;
    private static final String PREF_NAME="JWTTOKEN";
    private static final String isLogin="ISLOGIN";
    private static final String KEY_USER_NAME="username";
    private static final String Name="NAME";
    private static final String KEY_JWT_TOKEN="jwttoken";

    public TokenManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,privatemode);
        editor=sharedPreferences.edit();
    }

    public void createLoginSession(String username,String jwtvalue){
        editor.putBoolean(isLogin,true);
        editor.putString(KEY_USER_NAME,username);
        editor.putString(KEY_JWT_TOKEN,jwtvalue);
        editor.commit();

    }

    public HashMap getDetailLogin(){
        HashMap<String,String> map=new HashMap<>();
        map.put(KEY_USER_NAME,sharedPreferences.getString(KEY_USER_NAME,null));
        map.put(KEY_JWT_TOKEN,sharedPreferences.getString(KEY_JWT_TOKEN,null));
        return map;
    }

    public void checkLogin(){
        if(!this.isLogin())
        {
            Intent mainIntent =new Intent(context, LoginActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


        }


    }

    public Boolean isLogin(){
        return sharedPreferences.getBoolean(isLogin,false);
    }
}
