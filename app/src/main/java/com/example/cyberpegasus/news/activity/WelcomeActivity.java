package com.example.cyberpegasus.news.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.tokenmanager.TokenManager;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {
    TokenManager tokenManager;

    private static int SPLASH_TIME_OUT = 2000;
    //TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {if (!tokenManager.checkLogin()   )


                {
                    Intent mainIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                    HashMap<String, String> user = tokenManager.getDetailLogin();
                    String username = user.get(TokenManager.KEY_USER_NAME);
                    String jwttoken = user.get(TokenManager.KEY_JWT_TOKEN);
                    mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_NEW_TASK);
                    mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }

             else {
                Intent loginIntent = new Intent(WelcomeActivity.this, DashboardActivity.class);
                startActivity(loginIntent);
                finish();






                //startActivity(getIntent());
            }

            }
        }, SPLASH_TIME_OUT);
        tokenManager = new TokenManager(WelcomeActivity.this);


    }
}