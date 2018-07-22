package com.example.cyberpegasus.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormActivity extends AppBaseActivity {
    Button btnBodyReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        btnBodyReport = (Button) findViewById(R.id.buttonBodyReport);
        btnBodyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bodyReportIntent = new Intent(FormActivity.this, BodyReportActivity.class);
                startActivity(bodyReportIntent);
            }
        });
    }
}
