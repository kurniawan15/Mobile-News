package com.example.cyberpegasus.news.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
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
import com.example.cyberpegasus.news.network.BaseAPIService;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private ArrayList<File> listFile = new ArrayList<>();
    private ListView listView;
    File mediaFile;


    BaseAPIService service = RetrofitInstance.getRetrofitInstance().create(BaseAPIService.class);
    EditText pengirim,judul,datePengirim,dateBerita,catagory,isi,lanPengirim,lngPengirim,lanBerita;
    Button pick;
    TextView dateResult;
    int day,month,year,hour,minute;
    int finalDay,finalMonth, finalYear, finalHour, finalMinute;
    String address;

    double latBerita, lngBerita, latCurrent, lngCurrent;
    Uri mImageUri;

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
                uploadFiles(listFile);
                listFile.clear();
                list.clear();
                adapter = new MediaListAdapter(view.getContext(), R.layout.media_list_item, list, listFile);
                listView.setAdapter(adapter);
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
                    mImageUri = data.getData();
                    mediaFile = new File(getPathFromUri(this, mImageUri));
                    fileName = getFileName(mImageUri);
                    list.add(fileName);
                    listFile.add(mediaFile);

                    if (mediaFile != null) {
                        adapter = new MediaListAdapter(this, R.layout.media_list_item, list, listFile);
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
                            mediaFile = new File(getPathFromUri(this, uri));
                            fileName = getFileName(uri);
                            list.add(fileName);
                            listFile.add(mediaFile);
                        }
                        adapter = new MediaListAdapter(this, R.layout.media_list_item, list, listFile);
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
            listFile.add(mediaFile);
            adapter = new MediaListAdapter(this, R.layout.media_list_item, list, listFile);
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
            listFile.add(mediaFile);
            adapter = new MediaListAdapter(this, R.layout.media_list_item, list, listFile);
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

    //Method untuk mengambil path dari file gambar/video
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
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

    //Method untuk mengunggah file ke server dengan jumlah yang dinamik
    private void uploadFiles(List<File> mediaFiles) {
        //create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.222:9099/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        //get client and call object for the request
        BaseAPIService client = retrofit.create(BaseAPIService.class);

        List<MultipartBody.Part> parts = new ArrayList<>();

        for(int i = 0; i < mediaFiles.size(); i++) {
            parts.add(prepareFilePart("file", mediaFiles.get(i)));
            Toast.makeText(this, "File ada: " + i, Toast.LENGTH_SHORT).show();
        }

        //finally execute the request
        Call<ResponseBody> call = client.uploadPhoto(parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "terupload: ", Toast.LENGTH_LONG).show();
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "tidak terupload: ", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File mediaFile) {
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("video"),
                        mediaFile
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, mediaFile.getName(), requestFile);
    }

}
