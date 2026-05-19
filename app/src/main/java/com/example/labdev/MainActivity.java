package com.example.labdev;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView viewLatitude, viewLongitude, viewImei, viewStatus;
    private LocationManager gpsManager;
    private String deviceUniqueId;
    
    // Correction de la casse : CreatePosition.php avec un C majuscule
    private static final String ENDPOINT_URL = "http://192.168.1.23/localisation/CreatePosition.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewLatitude = findViewById(R.id.txt_lat);
        viewLongitude = findViewById(R.id.txt_lon);
        viewImei = findViewById(R.id.txt_device_id);
        viewStatus = findViewById(R.id.txt_status);

        deviceUniqueId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        viewImei.setText("ID Appareil : " + deviceUniqueId);

        startTracking();
    }

    private void startTracking() {
        gpsManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 102);
            return;
        }

        gpsManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                viewLatitude.setText("Latitude : " + lat);
                viewLongitude.setText("Longitude : " + lon);
                
                Toast.makeText(MainActivity.this, "Position détectée", Toast.LENGTH_SHORT).show();
                uploadToServer(lat, lon);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(@NonNull String provider) {}
            @Override
            public void onProviderDisabled(@NonNull String provider) {}
        });
    }

    private void uploadToServer(final double lat, final double lon) {
        viewStatus.setText("Statut : Connexion...");
        
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ENDPOINT_URL,
                response -> viewStatus.setText("Statut : " + response),
                error -> {
                    String errorMsg = "Erreur réseau";
                    if (error.networkResponse != null) {
                        errorMsg = "HTTP " + error.networkResponse.statusCode;
                    } else if (error.getMessage() != null) {
                        errorMsg = error.getMessage();
                    } else {
                        errorMsg = error.getClass().getSimpleName();
                    }
                    viewStatus.setText("Statut : " + errorMsg);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                
                params.put("latitude", String.valueOf(lat));
                params.put("longitude", String.valueOf(lon));
                params.put("date_position", sdf.format(new Date()));
                params.put("imei", deviceUniqueId);
                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 102) startTracking();
        }
    }
}
