package com.example.cyberpegasus.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.cyberpegasus.news.decode.JWTUtils;
import com.example.cyberpegasus.news.model.JWTToken;
import com.example.cyberpegasus.news.model.Login;
import com.example.cyberpegasus.news.network.BaseAPIService;
import com.example.cyberpegasus.news.network.RetrofitClient;
import com.example.cyberpegasus.news.tokenmanager.TokenManager;
import com.example.cyberpegasus.news.Encryption.md5;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.System.in;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    CardView loginBtn;
    TokenManager tokenManager;

    Context mContext;
    BaseAPIService mApiService;
    public static Call<String> usercall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;


        //initComponents();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (CardView) findViewById(R.id.login);
        tokenManager = new TokenManager(getApplicationContext());


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


                //final String passwordval = password.getText().toString();

                //MessageDigest mdEnc;


                Call<JWTToken> jwtTokenCall = baseAPIService.loginRequest(usernameval, passwordval);

                jwtTokenCall.enqueue(new Callback<JWTToken>() {
                    @Override
                    public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {
                        if(response.isSuccessful()) {

                            final JWTToken jwtToken = response.body();
                            Toast.makeText(mContext, "" + jwtToken.getToken().toString(), Toast.LENGTH_SHORT).show();
                            //tokenManager.createLoginSession(usernameval, jwtToken.getToken().toString());

                            Intent mainIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                        else {
                            Toast.makeText(mContext,"Login not correct :(",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JWTToken> call, Throwable t) {
                        Toast.makeText(mContext, "You are not authorized!" , Toast.LENGTH_SHORT).show();



                    }
                });
            }
        });


    }
}





