package com.example.cyberpegasus.news;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FormActivity extends AppBaseActivity {
    Button btnBodyReport, btnUpload;
    ImageButton toMaps;
    TextView loc, nmFoto;
    private int PICK_IMAGE_REQUEST = 1;
    private List<Uri> userSelectedImageUriList = null;
    private MediaListAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        loc = (TextView) findViewById(R.id.lokasi);

        nmFoto = (TextView) findViewById(R.id.namaFoto);

        listView = (ListView) findViewById(R.id.listViewMedia);

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
