package com.example.cyberpegasus.news.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.model.JWTToken;
import com.example.cyberpegasus.news.network.BaseAPIService;
import com.example.cyberpegasus.news.network.RetrofitClient;
import com.example.cyberpegasus.news.tokenmanager.TokenManager;
import com.example.cyberpegasus.news.Encryption.md5;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            public void onClick(View view) {



                    String user = null;
                    String pass = null;
                    user = username.getText().toString();
                    pass = password.getText().toString();
                    if(user.equals("gerry97") && pass.equals("gerry") || user.equals("jeremi") && pass.equals("admin") ){
                        Intent mainIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                    else{
                        Toast.makeText(view.getContext(), "Username atau password salah !", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }
}





