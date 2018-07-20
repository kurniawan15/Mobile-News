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

public class MainActivity extends AppCompatActivity {

    /*
    * ini adalah url ke ip jaringan
    * */
    public static final String URL_SAVE_NAME = "http://192.168.1.212/android/SKM/saveBerita.php";

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
    }
}
