package com.example.cyberpegasus.news.tokenmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
    //private static final String EXPIRED_AT="exp_at"
    private static final String Name="NAME";
    LoginActivity loginActivity;



    public TokenManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void storeLogin(String username,String jwtvalue){
        editor.putBoolean(isLogin,true);
        editor.putString(KEY_USER_NAME,username);
        editor.putString(KEY_JWT_TOKEN,jwtvalue);
        editor.commit();

    }

    public HashMap<String,String> getDetailLogin(){
        HashMap<String,String> map=new HashMap<>();
        map.put(KEY_USER_NAME,sharedPreferences.getString(KEY_USER_NAME,null));
        map.put(KEY_JWT_TOKEN,sharedPreferences.getString(KEY_JWT_TOKEN,null));
        return map;
    }

    public boolean checkLogin(){

        if(!this.isLogin()  )
        {
            //if (checkExp()) {


                Intent mainIntent = new Intent(context, DashboardActivity.class);
                mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(mainIntent);
                return true;

        }
       return false;

    }

    public Boolean isLogin(){
        return sharedPreferences.getBoolean(isLogin ,false);

    }


    public void logout()
    {
        editor.clear();
        editor.commit();
        Intent mainIntent=new Intent(context, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
        //sharedPreferences.getBoolean(isLogin,false);
    }

    public boolean checkExp()
    {
        loginActivity=new LoginActivity();
        HashMap<String,String> map=new HashMap<>();
        map.put(KEY_JWT_TOKEN,sharedPreferences.getString(KEY_JWT_TOKEN,null));
        String token =  KEY_JWT_TOKEN;
        JWT jwt=new JWT(token);
        boolean isExpired = jwt.isExpired(10); // 10 seconds leeway


        if (isExpired){
            sharedPreferences.getBoolean(isLogin,true);
            return true;

        }
        else {
            sharedPreferences.getBoolean(isLogin,false);
            return  false;
        }
    }

    public Date tellExpire(){
        loginActivity=new LoginActivity();
        HashMap<String,String> map=new HashMap<>();
        map.put(KEY_JWT_TOKEN,sharedPreferences.getString(KEY_JWT_TOKEN,null));
        String token =  KEY_JWT_TOKEN;
        JWT jwt=new JWT(token);
        Date expiresAt = jwt.getExpiresAt();
        //Toast.makeText(context,"Token habis sampai :"+expiresAt.toString(),Toast.LENGTH_SHORT).show();
        return expiresAt;
    }
}
