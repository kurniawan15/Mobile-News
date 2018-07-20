package com.example.cyberpegasus.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class BodyReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_report);

        Spinner kategoriSpinner = findViewById(R.id.dropdownKategori);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapter);
        kategoriSpinner.setSelection(2);
    }
}
