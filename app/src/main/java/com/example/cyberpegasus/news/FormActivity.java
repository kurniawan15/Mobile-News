package com.example.cyberpegasus.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FormActivity extends AppBaseActivity {
    Button btnBodyReport;
    ImageButton toMaps;
    TextView loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        loc = (TextView) findViewById(R.id.lokasi);

        Intent intent = getIntent();
        String address = intent.getStringExtra("ADDRESS");

        loc.setText(address);
        btnBodyReport = (Button) findViewById(R.id.buttonBodyReport);
        btnBodyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bodyReportIntent = new Intent(FormActivity.this, BodyReportActivity.class);
                startActivity(bodyReportIntent);
            }
        });

        toMaps = (ImageButton) findViewById(R.id.mapsButton);
        toMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapsIntent = new Intent(FormActivity.this, MapsActivity.class);
                startActivity(mapsIntent);
            }
        });
    }
}
