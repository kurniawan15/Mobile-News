package com.example.cyberpegasus.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    CardView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (CardView) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = null;
                String pass = null;
                user = username.getText().toString();
                pass = password.getText().toString();
                if(user.equals("gerry97") && pass.equals("gerry")){
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
