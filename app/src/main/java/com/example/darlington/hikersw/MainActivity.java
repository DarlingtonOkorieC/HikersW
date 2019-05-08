package com.example.darlington.hikersw;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;

    public void friendsYouMet(View view){
        Intent intent = new Intent(getApplicationContext(), FriendsActivity.class);
        intent.putExtra("username", "rob");


        startActivity(intent);
    }

    public void btnMemorable(View view){
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

        startActivity(intent);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startListening();


        }
    }

    public void startListening(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }



    public void updateLocationInfo(Location location){

        Log.i("locationInfo", location.toString());

        TextView latTextView = (TextView) findViewById(R.id.latTextview);

        TextView longTextView = (TextView) findViewById(R.id.longTextview);

        TextView accTextView = (TextView) findViewById(R.id.accTextview);

        TextView altTextView = (TextView) findViewById(R.id.accTextview);

        latTextView.setText("Latitude:" + location.getLatitude());

        longTextView.setText("Longitude:" + location.getLongitude());

        altTextView.setText("Longitude:" + location.getAltitude());

        accTextView.setText("Accuracy:" + location.getAccuracy());

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            String Address = "Could not find Address";

            List<android.location.Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (listAddresses != null && listAddresses.size() > 0) {

                Log.i("placeinfo", listAddresses.get(0).toString());

                Address = "Address:\n ";

                if (listAddresses.get(0).getSubThoroughfare() != null) {
                    Address += listAddresses.get(0).getSubThoroughfare() + " ";

                }


            }

            if (listAddresses.get(0).getThoroughfare() != null) {
                Address += listAddresses.get(0).getThoroughfare() + " \n";

            }

            if (listAddresses.get(0).getLocality() != null) {
                Address += listAddresses.get(0).getLocality() + " \n";

            }

            if (listAddresses.get(0).getPostalCode() != null) {
                Address += listAddresses.get(0).getPostalCode() + " \n";

            }

            if (listAddresses.get(0).getCountryName() != null) {
                Address += listAddresses.get(0).getCountryName() + " \n";

            }

            TextView addressTextView = (TextView) findViewById(R.id.Address);
            addressTextView.setText(Address);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocationInfo(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            startListening();
        }else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                    ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {


                    updateLocationInfo(location);



                }




            }

        }

    }



}

