package com.example.cyberpegasus.news;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cyberpegasus.news.activity.AppBaseActivity;
import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.model.LokBerita;
import com.example.cyberpegasus.news.model.LokPengirim;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                Bundle bundle = getIntent().getExtras();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.US);

                String sjudul = getIntent().getStringExtra("judul");
                String sdateBerita = getIntent().getStringExtra("tanggal");
                Double dlanPengirim = getIntent().getExtras().getDouble("lat_current");
                Double dlngPengirim = getIntent().getExtras().getDouble("lng_current");
                Double dlanBerita = getIntent().getExtras().getDouble("lat_berita");
                Double dlngBerita = getIntent().getExtras().getDouble("lng_berita");
                ArrayList<String> sFile = (ArrayList<String>) bundle.getStringArrayList("listFile");

                String sPengirim =  "kurniawan";    //type.getText().toString();

                String sdatePengirim = format.format(Calendar.getInstance().getTime());

                String sIsi = isi.getText().toString();
                String scatagori = kategoriSpinner.getSelectedItem().toString();

                try {
                    Date dateBerita = format.parse(sdateBerita);
                    Date datePengirim = format.parse(sdatePengirim);



                Log.d(sjudul, "Judul ");
                Log.d(sIsi, "Isinya  ");
                Log.d(scatagori, "Categori");
                Log.d(sPengirim, "Pengirimnya");
                Log.d(String.valueOf(sFile), "NamaFile");
                Log.d(String.valueOf(dateBerita), "Tanggal berita");
                Log.d(String.valueOf(datePengirim), "Pengirimnya ");
                Log.d(String.valueOf(dlanBerita), "lan berita ");
                Log.d(String.valueOf(dlngBerita), "long berita ");
                Log.d(String.valueOf(dlanPengirim), "lan pengirim");
                Log.d(String.valueOf(dlngPengirim), "long pengirim ");


                        LokBerita lokasiBerita = new LokBerita(dlanBerita,dlngBerita);
                        LokPengirim lokasiPengirim = new LokPengirim(dlanPengirim,dlngPengirim);

                        Data data = new Data(lokasiBerita,lokasiPengirim,datePengirim,dateBerita,sPengirim,scatagori,sIsi,sjudul,sFile);

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


                    } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }

        });

    }

}
