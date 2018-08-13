package com.example.cyberpegasus.news.tokenmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.cyberpegasus.news.activity.DashboardActivity;
import com.example.cyberpegasus.news.activity.LoginActivity;
import com.example.cyberpegasus.news.model.JWTToken;
import com.example.cyberpegasus.news.model.Login;
import com.example.cyberpegasus.news.network.BaseAPIService;
import com.example.cyberpegasus.news.network.RetrofitClient;

import java.security.acl.LastOwnerException;
import java.util.Date;
import java.util.HashMap;

import static com.example.cyberpegasus.news.activity.LoginActivity.jwttoken;

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

        if(!this.isLogin() )
        {
            Intent mainIntent =new Intent(context, DashboardActivity.class);
            //getDetailLogin();
            mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TOP);
            mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(mainIntent);
            return true;
        }
        else {
            //boolean isLogin =sharedPreferences.getBoolean("isLogin",isLogin() );
            //return isLogin;
            return false;
        }

    }

    public Boolean isLogin(){
        return sharedPreferences.getBoolean(isLogin,false);

    }


    public void logout()
    {
        editor.clear();
        editor.commit();
        Intent mainIntent=new Intent(context,LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
    }

    public boolean checkExp()
    {
        loginActivity=new LoginActivity();
        String token = LoginActivity.jwttoken;
        JWT jwt=new JWT(token);
        boolean isExpired = jwt.isExpired(10); // 10 seconds leeway

        if (isExpired){
            return sharedPreferences.getBoolean(isLogin,true);
        }
        else {
            return sharedPreferences.getBoolean(isLogin,false);
        }
    }

    public void tellExpire(){
        loginActivity=new LoginActivity();
        String token =  LoginActivity.jwttoken;
        JWT jwt=new JWT(token);Date expiresAt = jwt.getExpiresAt();
        Toast.makeText(context,"Token habis sampai :"+expiresAt.toString(),Toast.LENGTH_SHORT).show();
    }
}
