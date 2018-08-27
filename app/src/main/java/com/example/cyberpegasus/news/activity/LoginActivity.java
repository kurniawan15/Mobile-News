package com.example.cyberpegasus.news.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;
import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.model.JWTToken;
import com.example.cyberpegasus.news.network.BaseAPIService;
import com.example.cyberpegasus.news.network.RetrofitClient;
import com.example.cyberpegasus.news.tokenmanager.TokenManager;
import com.example.cyberpegasus.news.Encryption.md5;

import java.math.BigInteger;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cyberpegasus.news.tokenmanager.TokenManager.KEY_JWT_TOKEN;
import static com.example.cyberpegasus.news.tokenmanager.TokenManager.checkExp;


public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    CardView loginBtn;
    private TokenManager tokenManager;
    SharedPreferences sharedPreferences;
    public static String jwttoken;
    public static String usernameApp;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tokenManager = new TokenManager(LoginActivity.this);
        //final boolean isLogin = tokenManager.checkLogin();


        mContext = this;


        //initComponents();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (CardView) findViewById(R.id.login);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BaseAPIService baseAPIService = RetrofitClient.getbaseAPIService();
                final String usernameval = username.getText().toString();
                byte[] md5input = password.getText().toString().getBytes();
                BigInteger md5Data = null;
                try {
                    md5Data = new BigInteger(1, md5.encryptMD5(md5input));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String passwordval = md5Data.toString(16);
                //Toast.makeText(mContext, "User Login Status :" + TokenManager.isLogin.toString(), Toast.LENGTH_LONG).show();


                Call<JWTToken> jwtTokenCall = baseAPIService.loginRequest(usernameval, passwordval);

                jwtTokenCall.enqueue(new Callback<JWTToken>() {
                    @Override
                    public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {
                        if (response.isSuccessful()) {
                            usernameApp = username.getText().toString();
                            final JWTToken jwtToken = response.body();
                            jwttoken = jwtToken.getToken().toString();

                            tokenManager.storeLogin(usernameval, jwttoken);
                            //Toast.makeText(mContext, "" + jwttoken, Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Toast.makeText(mContext, "Login not correct :(", Toast.LENGTH_SHORT).show();
                        }
                        onBackPressed();


                    }


                    @Override
                    public void onFailure(Call<JWTToken> call, Throwable t) {
                        Toast.makeText(mContext, "You are not authorized!", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }


    public void onBackPressed(){
        finish();
        System.exit(0);
    }

}













