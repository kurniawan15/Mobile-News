package com.example.cyberpegasus.news.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.adapter.MediaListAdapter;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;
import com.example.cyberpegasus.news.network.UploadResponse;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FormActivity extends AppBaseActivity  implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Button btnBodyReport, btnUpload, btnUploadTest;
    ImageButton toMaps;
    TextView loc;
    private int PICK_IMAGE_REQUEST = 1;
    private int GET_ADDRESS_REQUEST = 7;
    private List<Uri> userSelectedImageUriList = null;
    private MediaListAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ListView listView;
    File mediaFile;


    GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
    EditText pengirim,judul,datePengirim,dateBerita,catagory,isi,lanPengirim,lngPengirim,lanBerita;
    Button pick;
    TextView dateResult;
    int day,month,year,hour,minute;
    int finalDay,finalMonth, finalYear, finalHour, finalMinute;
    String address;

    double latBerita, lngBerita, latCurrent, lngCurrent;

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

        listView = (ListView) findViewById(R.id.listViewMedia);

        /*Intent intent = getIntent();
        String address = intent.getStringExtra("ADDRESS");*/

        btnUploadTest = (Button) findViewById(R.id.buttonUploadTest);
        btnUploadTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uploadImage();
            }
        });

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



        judul = findViewById(R.id.judul);
        dateBerita= findViewById(R.id.tanggal);

            btnBodyReport = (Button) findViewById(R.id.buttonBodyReport);
            btnBodyReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String sdateBerita = dateBerita.getText().toString();
                    String sjudul = judul.getText().toString();

                    if(sjudul.equals("")){
                        judul.setError("Silahkan isi data");
                    }else if (sdateBerita.equals("")){
                        dateBerita.setError("Silahkan isi data");
                    }

                    Intent bodyReportIntent = new Intent(FormActivity.this, BodyReportActivity.class);
                    bodyReportIntent.putExtra("judul", sjudul);
                    bodyReportIntent.putExtra("tanggal", sdateBerita);
                    bodyReportIntent.putExtra("listFile", list);

                    Bundle bodyReportBundle = new Bundle();
                    bodyReportBundle.putDouble("lat_berita", latBerita);
                    bodyReportBundle.putDouble("lng_berita", lngBerita);
                    bodyReportBundle.putDouble("lat_current", latCurrent);
                    bodyReportBundle.putDouble("lng_current", lngCurrent);


                    startActivity(bodyReportIntent);

                }
            });


        toMaps = (ImageButton) findViewById(R.id.mapsButton);
        toMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapsIntent = new Intent(FormActivity.this, MapsActivity.class);
                startActivityForResult(mapsIntent, GET_ADDRESS_REQUEST);
                //startActivity(mapsIntent);
            }
        });

        btnUpload = (Button) findViewById(R.id.buttonUpload);

        //Menonaktifkan tombol apabila kamera pengguna tidak berfungsi
        if (!hasCamera())
            btnUpload.setEnabled(false);

    }

    public void launchCamera(View view) {
        final CharSequence opt[] = new CharSequence[] {"Gallery", "Ambil Foto", "Ambil Video"};

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Media");
        builder.setItems(opt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // Show only images, no videos or anything else
                        intent.setType("image/* video/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        // Always show the chooser (if there are multiple options available)
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        break;
                    case 1:
                        ImagePicker ip = new ImagePicker.Builder(FormActivity.this)
                                .mode(ImagePicker.Mode.CAMERA)
                                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                                .directory(ImagePicker.Directory.DEFAULT)
                                .extension(ImagePicker.Extension.PNG)
                                .scale(600, 600)
                                .allowMultipleImages(false)
                                .enableDebuggingMode(true)
                                .build();
                        break;
                    case 2:
                        VideoPicker vp = new VideoPicker.Builder(FormActivity.this)
                                .mode(VideoPicker.Mode.CAMERA)
                                .directory(VideoPicker.Directory.DEFAULT)
                                .extension(VideoPicker.Extension.MP4)
                                .enableDebuggingMode(true)
                                .build();
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String fileName = "";

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                //If Single image selected then it will fetch from Gallery
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    fileName = getFileName(mImageUri);

                    //Untuk mendapatkan path directory dari file yang diambil
                    String mediaPath = mImageUri.getPath();
                    Toast.makeText(this, "Media disimpan ke:\n" + mediaPath, Toast.LENGTH_LONG).show();
                    mediaFile = new File(mediaPath);
                    list.add(fileName);
                    if (mediaFile != null) {
                        adapter = new MediaListAdapter(this, R.layout.media_list_item, list);
                        listView.setAdapter(adapter);
                    }
                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData=data.getClipData();
                        ArrayList<Uri> mArrayUri=new ArrayList<Uri>();
                        for(int i=0;i<mClipData.getItemCount();i++){
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            String mediaPath = uri.getPath();
                            File mediaFile = null;
                            mediaFile = new File(mediaPath);
                            fileName = getFileName(uri);
                            list.add(fileName);
                        }
                        adapter = new MediaListAdapter(this, R.layout.media_list_item, list);
                        listView.setAdapter(adapter);
                        Toast.makeText(this, mArrayUri.size() + " File Ditambahkan!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            //Get the photo path
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            mediaFile = null;
            mediaFile = new File(mPaths.get(0));
            fileName = mediaFile.getName();
            list.add(fileName);
            adapter = new MediaListAdapter(this, R.layout.media_list_item, list);
            listView.setAdapter(adapter);
            Toast.makeText(FormActivity.this,fileName + " Ditambahkan !", Toast.LENGTH_LONG).show();
        }
        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            //Get the video path
            List<String> mPaths = (List<String>) data.getSerializableExtra(VideoPicker.EXTRA_VIDEO_PATH);
            File mediaFile = null;
            mediaFile = new File(mPaths.get(0));
            fileName = mediaFile.getName();
            list.add(fileName);
            adapter = new MediaListAdapter(this, R.layout.media_list_item, list);
            listView.setAdapter(adapter);
            Toast.makeText(FormActivity.this,fileName + " Ditambahkan !", Toast.LENGTH_LONG).show();
        }
        if (requestCode == GET_ADDRESS_REQUEST) {
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                address = extras.getString("ADDRESS");
                latBerita = extras.getDouble("lat_berita");
                lngBerita = extras.getDouble("lng_berita");
                latCurrent = extras.getDouble("lat_current");
                lngCurrent = extras.getDouble("lng_current");

                System.out.println("Latber : " + latBerita + "Lngber : " + lngBerita + "Latcur : " + latCurrent + "Lngcur : " + lngCurrent);

                loc.setText(address);
            }
        }
    }

    //Method untuk mendapatkan nama file dari bentuk Uri
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

    //Method untuk memeriksa apakah kamera pada perangkat pengguna berfungsi
    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("judul", judul.getText().toString());
        outState.putString("tanggal", dateBerita.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String juduls = (String) savedInstanceState.getString("judul");
        String tanggal = (String) savedInstanceState.getString("tanggal");
        Toast.makeText(FormActivity.this, "Judul: " + judul, Toast.LENGTH_LONG).show();
        judul.setText(juduls);
        dateBerita.setText(tanggal);
    }

    private void uploadImage() {

        //File file = new File(selectImagePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image"), mediaFile);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", mediaFile.getName(), reqFile);
        RequestBody ImageName = RequestBody.create(MediaType.parse("text/plain"), mediaFile.getName());
        /*Retrofit req = new retrofit2.Retrofit.Builder()
                .baseUrl("http://192.168.1.241/restAPI/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
        Retrofit req = RetrofitInstance.getRetrofitInstance();
        GetDataService request = req.create(GetDataService.class);
        //RequestInterface request = RetrofitClientUtil.getRequestInterface();
        Call<UploadResponse> responseCall = request.postImage(imageBody, ImageName);
        responseCall.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.isSuccessful()) {
                    UploadResponse resp = response.body();
                    if (resp.getCode() == 200) {
                        Toast.makeText(FormActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                        /*mSnackbar = Snackbar.make(mParent, resp.getMessage(), Snackbar.LENGTH_LONG);
                        View views = mSnackbar.getView();
                        views.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                        mSnackbar.show();*/
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                /*mSnackbar = Snackbar.make(mParent, t.getLocalizedMessage(), Snackbar.LENGTH_LONG);
                View views = mSnackbar.getView();
                views.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorWarmGrey));
                mSnackbar.show();*/
            }
        });


    }

}
