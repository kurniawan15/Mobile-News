package com.example.cyberpegasus.news;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppBaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btnFinish, btnGetAddress;
    EditText txtAddress;
    TextView tv;
    Float zoom = 16.0f;
    LocationManager locationManager;
    String provider;
    double lat, lng;
    final int MY_PERMISSION_REQUEST_CODE = 7171;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtAddress = (EditText) findViewById(R.id.txtAddress);
        btnFinish = (Button) findViewById(R.id.buttonMapsFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = txtAddress.getText().toString();
                Intent formIntent = new Intent(MapsActivity.this, FormActivity.class);
                formIntent.putExtra("ADDRESS", address);
                startActivity(formIntent);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }

        btnGetAddress = (Button) findViewById(R.id.buttonGetAddress);
        btnGetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lat = -6.863257;
                lng = 107.584040;
                new GetAddress().execute(String.format("%.4f,%.4f",lat,lng));
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        LatLng sembada = new LatLng(-6.863257, 107.584040);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sembada));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sembada, zoom));
        /*mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng midLatLng = mMap.getCameraPosition().target;
                String[] midLatLngStr = midLatLng.toString().split(",");
                lat = Double.parseDouble(midLatLngStr[0]);
                lng = Double.parseDouble(midLatLngStr[1]);
            }
        });*/
    }

    public void geoLocate(View view) throws IOException {
        String location = txtAddress.getText().toString();

        Geocoder gc = new Geocoder(this);
        try {
            List<Address> list = gc.getFromLocationName(location, 1);
            Address address = list.get(0);
            String locality = address.getLocality();

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            LatLng ll = new LatLng(lat, lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, zoom));
        } catch (NullPointerException ne) {
            Toast.makeText(this, "Alamat tidak ditemukan !", Toast.LENGTH_LONG).show();
        }

    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final Location location = locationManager.getLastKnownLocation(provider);
        if(location == null)
            Log.e("ERROR", "Location is null");
    }

    private class GetAddress extends AsyncTask<String,Void,String>{
        ProgressDialog dialog = new ProgressDialog(MapsActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false", lat, lng);
                response = http.GetHttpData(url);
                return response;
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                String address = ((JSONArray)jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                //tv.setText(address);
                txtAddress.setText(address);
            }catch (JSONException e){
                e.printStackTrace();
            }

            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
