package com.example.cyberpegasus.news;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cyberpegasus.news.activity.AppBaseActivity;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BodyReportActivity extends AppBaseActivity {
    GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
    EditText pengirim,judul,datePengirim,dateBerita,catagory,isi;
    Spinner kategoriSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_report);

        kategoriSpinner = findViewById(R.id.dropdownKategori);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapter);
        kategoriSpinner.setSelection(2);
        isi = findViewById(R.id.isiBerita);

        findViewById(R.id.buttonSubmitReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.US);

                String sjudul = getIntent().getStringExtra("judul");
                String sdateBerita = getIntent().getStringExtra("tanggal");
                Double dlanPengirim = getIntent().getExtras().getDouble("lat_current");
                Double dlngPengirim = getIntent().getExtras().getDouble("lng_current");
                Double dlanBerita = getIntent().getExtras().getDouble("lat_berita");
                Double dlngBerita = getIntent().getExtras().getDouble("lng_berita");

                String sPengirim =  "kurniawan";    //type.getText().toString();
                String sdatePengirim = format.format(Calendar.getInstance().getTime());

                String sIsi = isi.getText().toString();
                String scatagori = kategoriSpinner.getSelectedItem().toString();





                Log.d(sjudul, ":Judul ");
                Log.d(sIsi, ":Isinya  ");
                Log.d(scatagori, ":Categori");
                Log.d(sPengirim, ":Pengirimnya");
                Log.d(sdateBerita, ":Tanggal berita");
                Log.d(sdatePengirim, ":Pengirimnya ");
                Log.d(String.valueOf(dlanBerita), ":lan berita ");
                Log.d(String.valueOf(dlngBerita), ":long berita ");
                Log.d(String.valueOf(dlanPengirim), ":lan pengirim");
                Log.d(String.valueOf(dlngPengirim), ":long pengirim ");

                    finish();


                        /*

                        LokBerita lokasiBerita = new LokBerita(slan,slng);
                        LokPengirim lokasiPengirim = new LokPengirim(slan,slng);
                        Data data = new Data(spesan,scatagori,dateString,stype,sdari,lokasi);

                        //  Call<DataList> call = service.AddData(sdari,stype,dateString,scatagori,spesan,lokasi);

                        Call<DataList> call = service.AddData(data);



                        call.enqueue(new Callback<DataList>(){
                            @Override
                            public void onResponse(Call<DataList> call, Response<DataList> response) {
                                String msg = response.body().getMsg();


                                Toast.makeText(BodyReportActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();

                            }

                            @Override
                            public void onFailure(Call<DataList> call, Throwable t) {
                                Toast.makeText(BodyReportActivity.this, "Gagal menyambungkan ke Jaringan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                     */


            }

        });

    }

}
