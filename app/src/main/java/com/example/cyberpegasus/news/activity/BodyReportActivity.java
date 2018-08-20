package com.example.cyberpegasus.news.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.database.DatabaseHelper;
import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.model.LokBerita;
import com.example.cyberpegasus.news.model.LokPengirim;
import com.example.cyberpegasus.news.network.BaseAPIService;
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
    BaseAPIService service = RetrofitInstance.getRetrofitInstance().create(BaseAPIService.class);
    EditText pengirim,judul,DatePengirim,DateBerita,catagory,isi;
    Spinner kategoriSpinner, subKategori1Spinner, subKategori2Spinner;
    Intent intent;
    DatabaseHelper db;
    DataList dataList = new DataList();

    //1 means data is synced and 0 means data is not synced
    public static final int DATA_SYNCED_WITH_SERVER = 1;
    public static final int DATA_NOT_SYNCED_WITH_SERVER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_report);
        db = new DatabaseHelper(this);
        intent = new Intent(BodyReportActivity.this, DashboardActivity.class);
        kategoriSpinner = findViewById(R.id.dropdownKategori);
        subKategori1Spinner = findViewById(R.id.dropdownSubKategori1);
        subKategori2Spinner = findViewById(R.id.dropdownSubKategori2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapter);

        //Menggunakan listener agar data pada spinner dapat memunculkan spinner yang lain
        kategoriSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()) {
                    case "Kriminal":
                        ArrayAdapter<CharSequence> adapterSubKriminal = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_kriminal,
                                android.R.layout.simple_spinner_item);
                        adapterSubKriminal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubKriminal);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik":
                        ArrayAdapter<CharSequence> adapterSubKonflik = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik,
                                android.R.layout.simple_spinner_item);
                        adapterSubKonflik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubKonflik);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Pelanggaran Kedaulatan":
                        ArrayAdapter<CharSequence> adapterSubPelKedaulatan = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_pelanggaran_kedaulatan,
                                android.R.layout.simple_spinner_item);
                        adapterSubPelKedaulatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubPelKedaulatan);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Kecelakaan":
                        ArrayAdapter<CharSequence> adapterSubKecelakaan = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_kecelakaan,
                                android.R.layout.simple_spinner_item);
                        adapterSubKecelakaan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubKecelakaan);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Bencana":
                        ArrayAdapter<CharSequence> adapterSubBencana = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_bencana,
                                android.R.layout.simple_spinner_item);
                        adapterSubBencana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubBencana);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    default:
                        subKategori1Spinner.setAdapter(null);
                        subKategori1Spinner.setVisibility(Spinner.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subKategori1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()) {
                    case "Kejahatan Kekayaan Negara":
                        ArrayAdapter<CharSequence> adapterSubKKN = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_kejahatan_kekayaan_negara,
                                android.R.layout.simple_spinner_item);
                        adapterSubKKN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterSubKKN);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Bencana Ulah Manusia":
                        ArrayAdapter<CharSequence> adapterSubBencanaManusia = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_bencana_ulah_manusia,
                                android.R.layout.simple_spinner_item);
                        adapterSubBencanaManusia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterSubBencanaManusia);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Bencana Alam":
                        ArrayAdapter<CharSequence> adapterSubBencanaAlam = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_bencana_alam,
                                android.R.layout.simple_spinner_item);
                        adapterSubBencanaAlam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterSubBencanaAlam);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik Bersenjata":
                        ArrayAdapter<CharSequence> adapterKonflikBersenjata = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik_bersenjata,
                                android.R.layout.simple_spinner_item);
                        adapterKonflikBersenjata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterKonflikBersenjata);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik Horisontal":
                        ArrayAdapter<CharSequence> adapterKonflikHorisontal = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik_horisontal,
                                android.R.layout.simple_spinner_item);
                        adapterKonflikHorisontal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterKonflikHorisontal);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik Vertikal":
                        ArrayAdapter<CharSequence> adapterKonflikVertikal = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik_vertikal,
                                android.R.layout.simple_spinner_item);
                        adapterKonflikVertikal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterKonflikVertikal);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    default:
                        subKategori2Spinner.setAdapter(null);
                        subKategori2Spinner.setVisibility(Spinner.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*String scatagori = kategoriSpinner.getSelectedItem().toString() + ", "
                + subKategori1Spinner.getSelectedItem().toString() + ", "
                + subKategori2Spinner.getSelectedItem().toString();
        System.out.println(scatagori);*/

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

                String sPengirim =  "Pega";    //Di ganti ketika login selesai

                String sdatePengirim = format.format(Calendar.getInstance().getTime());

                String sIsi = isi.getText().toString();
                String scatagori = kategoriSpinner.getSelectedItem().toString();
                String subKategori1 = null;
                String subKategori2 = null;

                try {
                    if (subKategori1Spinner.getSelectedItem().toString() != null) {
                        subKategori1 = subKategori1Spinner.getSelectedItem().toString();
                        if (!subKategori1.contains("All")) {
                            scatagori = scatagori + ", " + subKategori1;
                        }
                        if (subKategori2Spinner.getSelectedItem().toString() != null) {
                            subKategori2 = subKategori2Spinner.getSelectedItem().toString();
                            if (!subKategori2.contains("All")) {
                                scatagori = scatagori + ", " + subKategori2;
                            }
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Date dDateBerita = format.parse(sdateBerita);
                    Date dDatePengirim = format.parse(sdatePengirim);



                Log.d(sjudul, "Judul ");
                Log.d(sIsi, "Isinya  ");
                Log.d(scatagori, "Categori");
                Log.d(sPengirim, "Pengirimnya");
                Log.d(String.valueOf(sFile), "NamaFile");
                Log.d(String.valueOf(dDateBerita), "Tanggal berita");
                Log.d(String.valueOf(dDatePengirim), "Pengirimnya ");
                Log.d(String.valueOf(dlanBerita), "lan berita ");
                Log.d(String.valueOf(dlngBerita), "long berita ");
                Log.d(String.valueOf(dlanPengirim), "lan pengirim");
                Log.d(String.valueOf(dlngPengirim), "long pengirim ");

                saveData(dlanPengirim, dlngPengirim, dlanBerita, dlngBerita,
                            sPengirim, sjudul, dDateBerita, dDatePengirim, scatagori,sIsi,sFile);

                startActivity(intent);


                    } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }

        });

    }

    public boolean cekKoneksi() {
        boolean status;
        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting () || mobile.isConnectedOrConnecting () ) {
            Toast.makeText(this, "Wifi or Mobile data", Toast.LENGTH_LONG).show();
            status = true;
        }
        else {
            Toast.makeText(this, "No Network ", Toast.LENGTH_LONG).show();
            status = false;
        }
        return status;
    }


public void saveData(Double dlanPengirim,Double dlngPengirim, Double dlanBerita, Double dlngBerita, String sPengirim,
                  String sjudul,Date dDateBerita,Date dDatePengirim, String scatagori, String sIsi,ArrayList<String> sFile) {

    boolean kon = cekKoneksi();

    //Terkoneksi ke internet
    if (kon == true) {
        boolean isInsert = db.addDataLokal(sjudul, sPengirim, dDatePengirim, scatagori, sIsi, dDateBerita,
                dlanPengirim, dlngPengirim, dlanBerita, dlngBerita, sFile, DATA_SYNCED_WITH_SERVER);
        if (isInsert == true) {
            Toast.makeText(BodyReportActivity.this, "Data sukses tersimpan dilocal", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(BodyReportActivity.this, "Data gagal tersimpan dilocal", Toast.LENGTH_LONG).show();
        }

        addToAPI(dlanPengirim, dlngPengirim, dlanBerita, dlngBerita,
                sPengirim, sjudul, dDateBerita, dDatePengirim, sIsi, scatagori, sFile);

    }

    //tidak terkoneksi ke internet
    else {

        boolean isInsert = db.addDataLokal(sjudul, sPengirim, dDatePengirim, scatagori, sIsi, dDateBerita,
                dlanPengirim, dlngPengirim, dlanBerita, dlngBerita, sFile, DATA_NOT_SYNCED_WITH_SERVER);
        if (isInsert == true) {
            Toast.makeText(BodyReportActivity.this, "Data sukses tersimpan dilocal", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(BodyReportActivity.this, "Data gagal tersimpan dilocal", Toast.LENGTH_LONG).show();
        }
    }
}




    //menyimpan ke API


    public void addToAPI(Double dlanPengirim,Double dlngPengirim, Double dlanBerita, Double dlngBerita, String sPengirim,
                     String sjudul,Date dDateBerita,Date dDatePengirim, String scatagori, String sIsi,ArrayList<String> sFile){

        Call<DataList> call = service.AddData(dlanPengirim,dlngPengirim,dlanBerita,dlngBerita,
                sPengirim,sjudul,dDateBerita,dDatePengirim,sIsi,scatagori,sFile);


        call.enqueue(new Callback<DataList>(){
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                String msg = response.body().getMsg();


               Toast.makeText(BodyReportActivity.this, msg, Toast.LENGTH_SHORT).show();
                //      finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Toast.makeText(BodyReportActivity.this, "Gagal menyambungkan ke Jaringan", Toast.LENGTH_SHORT).show();
            }
        });

    }




}
