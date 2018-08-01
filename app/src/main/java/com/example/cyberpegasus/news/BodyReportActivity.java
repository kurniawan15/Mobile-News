package com.example.cyberpegasus.news;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cyberpegasus.news.activity.AppBaseActivity;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;

public class BodyReportActivity extends AppBaseActivity {
    GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_report);

        final Spinner kategoriSpinner = findViewById(R.id.dropdownKategori);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapter);
        kategoriSpinner.setSelection(2);

    /*
        findViewById(R.id.buttonSubmitReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.US);


                dari = findViewById(R.id.judul); //harusnya form dari
                //type = findViewById(R.id.judul); // harusnya form type
                date = findViewById(R.id.tanggal);
                //     catagory = findViewById(R.id.catagory);
                pesan = findViewById(R.id.isiBerita);
                //lan =  findViewById(R.id.lan);
                //lng =  findViewById(R.id.lng);

                String sdari = dari.getText().toString();
                String stype =  "private";    //type.getText().toString();
                String sdate = date.getText().toString();
                String scatagori = kategoriSpinner.toString();

                String spesan = pesan.getText().toString();

                Double slan = 0.7;//Float.parseFloat(lng.getText().toString());
                Double slng = 98.8;//Float.parseFloat(lng.getText().toString());

                try {
                    Date dateString = format.parse(sdate);
                    System.out.println("ini isi datanya" + dateString);



                    if(sdari.equals("")){
                        dari.setError("Silahkan isi data");
                    }else if (stype.equals("")){
                        type.setError("Silahkan isi data");
                    }else if (scatagori.equals("")){
                        catagory.setError("Silahkan isi data");
                    }else if (spesan.equals("")){
                        pesan.setError("Silahkan isi data");
                    }else {

                        Lokasi lokasi = new Lokasi(slan,slng);
                        Data data = new Data(spesan,scatagori,dateString,stype,sdari,lokasi);

                        //  Call<DataList> call = service.AddData(sdari,stype,dateString,scatagori,spesan,lokasi);

                        Call<DataList> call = service.AddData(data);



                        call.enqueue(new Callback<DataList>(){
                            @Override
                            public void onResponse(Call<DataList> call, Response<DataList> response) {
                                String msg = response.body().getMsg();
                                String value = response.body().getValue();

                                Toast.makeText(BodyReportActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();

                            }

                            @Override
                            public void onFailure(Call<DataList> call, Throwable t) {
                                Toast.makeText(BodyReportActivity.this, "Gagal menyambungkan ke Jaringan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });
    */
    }
}
