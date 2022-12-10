package com.example.cafeapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.example.cafeapp.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity {
// implements OnMapReadyCallback
    //private GoogleMap mMap;
    //private ActivityMapsBinding binding;
    public String lat;
    public String lon;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("cords", MODE_PRIVATE);
        String cords = sharedPreferences.getString("cords", "");
        Log.i("---CORDS FOR MAP -- ", cords.toString());
        String[] splitCord = cords.split(",");
        Log.i("---CORDS SPLIT -- ", splitCord[0] + " " + splitCord[1]);
        lat = splitCord[0].substring(1);
        lon = splitCord[1].substring(0, splitCord[1].length()-1);
        Log.i("---CORDS SPLIT AGAIN-- ", lat + " " + Double.parseDouble(lat) + " " + lon + " " + Double.parseDouble(lon));

        SharedPreferences sharedPreferenceName = getSharedPreferences("name", MODE_PRIVATE);
        name = sharedPreferenceName.getString("name", "");
        Log.i("---NAME FOR MAP -- ", name.toString());

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+lat+","+lon+"?"+name));
        intent.setPackage("com.google.android.apps.maps");

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }


        /*
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
  */
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
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        mMap.addMarker(new MarkerOptions().position(location).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
    */
}