package com.example.cyberpegasus.news;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cyberpegasus.news.Adapter.MediaListAdapter;
import com.example.cyberpegasus.news.activity.AppBaseActivity;
import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.model.Lokasi;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppBaseActivity  implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Button btnBodyReport, btnUpload;
    ImageButton toMaps;
    TextView loc, nmFoto;
    private int PICK_IMAGE_REQUEST = 1;
    private List<Uri> userSelectedImageUriList = null;
    private MediaListAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ListView listView;


    GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
    EditText dari,type,date,catagory,pesan,lan,lng;
    Button pick;
    TextView dateResult;
    int day,month,year,hour,minute;
    int finalDay,finalMonth, finalYear, finalHour, finalMinute;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        finalYear = i;
        finalMonth = i1;
        finalDay = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(FormActivity.this, FormActivity.this,
                hour,minute, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        finalHour = i;
        finalMinute = i1;

        dateResult.setText(finalYear+"-"+finalMonth+"-"+finalDay+
                "/"+finalHour+": "+finalMinute+":00");

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        loc = (TextView) findViewById(R.id.lokasi);

        nmFoto = (TextView) findViewById(R.id.namaFoto);

        listView = (ListView) findViewById(R.id.listViewMedia);

        Intent intent = getIntent();
        String address = intent.getStringExtra("ADDRESS");

        pick = (Button) findViewById(R.id.btn_date);
        dateResult = (EditText) findViewById(R.id.tanggal);

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this,FormActivity.this,
                        year,month,day);
                datePickerDialog.show();
            }
        });

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

        btnUpload = (Button) findViewById(R.id.buttonUpload);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Show only images, no videos or anything else
                intent.setType("image/* video/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        dari = findViewById(R.id.judul); //harusnya form dari
        //type = findViewById(R.id.judul); // harusnya form type
        date = findViewById(R.id.tanggal);
   //     catagory = findViewById(R.id.catagory);
        pesan = findViewById(R.id.isiBerita);
        //lan =  findViewById(R.id.lan);
        //lng =  findViewById(R.id.lng);



        findViewById(R.id.buttonSubmitReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.US);

                String sdari = dari.getText().toString();
                String stype =  "private";    //type.getText().toString();
                String sdate = date.getText().toString();
                String scatagori =  "Berita";  //  catagory.getText().toString();
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

                                Toast.makeText(FormActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();

                            }

                            @Override
                            public void onFailure(Call<DataList> call, Throwable t) {
                                Toast.makeText(FormActivity.this, "Gagal menyambungkan ke Jaringan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String fileName = "";

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                //data.getParcelableArrayExtra(name);
                //If Single image selected then it will fetch from Gallery
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    fileName = getFileName(mImageUri);
                    String mediaPath = mImageUri.getPath();
                    File mediaFile = null;
                    mediaFile = new File(mediaPath);
                    list.add(fileName);
                    if (mediaFile != null) {
                        adapter = new MediaListAdapter(this, R.layout.media_list_item, list);
                        listView.setAdapter(adapter);
                        //nmFoto.setText(fileName + " " + mediaPath);
                        Toast.makeText(this, "File Masuk!!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData=data.getClipData();
                        ArrayList<Uri> mArrayUri=new ArrayList<Uri>();
                        for(int i=0;i<mClipData.getItemCount();i++){
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            fileName = fileName + "\n" + getFileName(uri);
                            list.add(getFileName(uri));
                        }
                        //nmFoto.setText(fileName);
                        Log.v("LOG_TAG", "Selected Images"+ mArrayUri.size());
                        adapter = new MediaListAdapter(this, R.layout.media_list_item, list);
                        listView.setAdapter(adapter);
                        Toast.makeText(this, "File Masuk!!" + mArrayUri.size(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
