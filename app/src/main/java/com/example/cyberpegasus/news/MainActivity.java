package com.example.cyberpegasus.news;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    * ini adalah url ke ip jaringan
    * */
    public static final String URL_SAVE_NAME = "http://192.168.43.210/android/SKM/view.php";

    TextView tvResultNama;
    String resultNama;

    //database helper object
    private DatabaseHelper db;

    //View objects
    private Button buttonSave;
    private EditText editTextName;
    private ListView listViewNames;

    //List to store all the judul
    private List<Name> names;

    //1 berarti data disinkronkan dan 0 berarti data tidak disinkronkan
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    //sebuah siaran untuk mengetahui apakah data tersebut disinkronkan atau tidak
    public static final String DATA_SAVED_BROADCAST = "Data tersingkronkan";

    //Broadcast receiver untuk mengetahui status sinkronisasi
    private BroadcastReceiver broadcastReceiver;

    //objek adaptor untuk tampilan daftar
    private NameAdapter nameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //menginisialisasi tampilan dan objek
        db = new DatabaseHelper(this);
        names = new ArrayList<>();

        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        listViewNames = (ListView) findViewById(R.id.listViewNames);

        //amenambahkan tombol listener ke button
        buttonSave.setOnClickListener(this);

        //memanggil metode untuk memuat semua data yang tersimpan
        loadNames();

        //menyiarkan untuk memperbarui status sinkronisasi
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //memuat data lagi
                loadNames();
            }
        };

        //mendaftarkan penerima untuk memperbarui status sinkronisasi
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
    }

    /*
    * metode ini akan
    * memuat nama-nama dari database
    * dengan status sinkronisasi yang diperbarui
    */
    private void loadNames() {
        names.clear();
        Cursor cursor = db.getNames();
        if (cursor.moveToFirst()) {
            do {
                Name name = new Name(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                );

                names.add(name);
            } while (cursor.moveToNext());
        }

        nameAdapter = new NameAdapter(this, R.layout.name, names);
        listViewNames.setAdapter(nameAdapter);
    }

    /*
    * Metode ini untuk merefresh daftar
    * */
    private void refreshList() {
        nameAdapter.notifyDataSetChanged();
    }

    /*
    * metode ini menyimpan data ke server
    * */
    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name...");
        progressDialog.show();

        final String name = editTextName.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                // jika berhasil
                                // menyimpan data ke sqlite dengan status yang disinkronkan
                                saveNameToLocalStorage(name, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //jika berhasil error
                                //menyimpan data ke sqlite dengan status tidak disinkronkan
                                saveNameToLocalStorage(name, NAME_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //error menyimpan nama ke sqlite dengan status tidak disinkronkan
                        saveNameToLocalStorage(name, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    // simpan nama ke penyimpanan lokal
    private void saveNameToLocalStorage(String name, int status) {
        editTextName.setText("");
        db.addName(name, status);
        Name n = new Name(name, status);
        names.add(n);
        refreshList();
    }

    @Override
    public void onClick(View view) {
        saveNameToServer();
    }
}
