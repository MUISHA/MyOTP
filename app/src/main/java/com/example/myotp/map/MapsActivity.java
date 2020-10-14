package com.example.myotp.map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myotp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng latLng;
    private Button fb;
    private EditText editTextLat,editTextLong;
    private TextView textView;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        textView = findViewById(R.id.textView);
        editTextLat = findViewById(R.id.editTextLat);
        editTextLong = findViewById(R.id.editTextong);

        fb = (Button) findViewById(R.id.buttongetlocation);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationDetails(view);
            }
        });
        latLng = new LatLng(-1.6766, 29.2306);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);



    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
        LatLng mergency = new LatLng(-1.6766, 29.2306);
        mMap.addMarker(new MarkerOptions().position(mergency).title(getString(R.string.emergecy)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mergency));
        mMap.setMaxZoomPreference(15);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.emergecy)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.setMaxZoomPreference(15);
                    String PhoneNumber = "+243" + "970117312";
                    String myLatitude = String.valueOf(location.getLatitude());
                    String myLongitude = String.valueOf(location.getLongitude());

                    String message = "Latitude" + myLatitude + "Longitude";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PhoneNumber, null,message, null, null);


                }catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME , MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME , MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_TIME , MIN_DIST, locationListener);
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    public void getLocationDetails(View view){
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        if (!(editTextLong.getText().toString().isEmpty() || editTextLat.getText().toString().isEmpty()))
        {
            latitude = Double.parseDouble(editTextLat.getText().toString());
            longitude = Double.parseDouble(editTextLong.getText().toString());
            latLng = new LatLng(latitude, longitude);
        }
        Geocoder geocoder;
        List<Address>addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String knonName = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knonName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title("Emergency SOS : " + address + city + state + country + postalCode + knonName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        textView.setText(address + city + state + country + postalCode + knonName);
    }

}