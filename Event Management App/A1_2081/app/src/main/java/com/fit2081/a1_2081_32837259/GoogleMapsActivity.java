package com.fit2081.a1_2081_32837259;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.a1_2081_32837259.databinding.ActivityGoogleMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ActivityGoogleMapsBinding binding;
    private String eventLocation;
    private Geocoder geocoder;
    SupportMapFragment mapFragment;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        eventLocation = intent.getStringExtra("event_location");

        geocoder = new Geocoder(this, Locale.getDefault());

        mapView = (MapView) findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
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
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        if (eventLocation != null && !eventLocation.isEmpty()) {
            try {
                List<Address> addresses = geocoder.getFromLocationName(eventLocation, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();

                    LatLng country = new LatLng(latitude, longitude);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(country, 10.0f));
                    googleMap.addMarker(new MarkerOptions().position(country).title(eventLocation));

                } else if (addresses != null ){
                    // If address is not found, default to Malaysia
                    Toast.makeText(GoogleMapsActivity.this, "Category address not found", Toast.LENGTH_LONG).show();
                    eventLocation = "Malaysia";
                    double latitude = 4.2105;
                    double longitude = 101.9758;
                    LatLng country = new LatLng(latitude, longitude);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(country, 10.0f));
                    googleMap.addMarker(new MarkerOptions().position(country).title("Malaysia"));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (eventLocation != null) {
            // if location is empty, default to Malaysia
            eventLocation = "Malaysia";
            try {
                List<Address> addresses = geocoder.getFromLocationName(eventLocation, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();

                    LatLng country = new LatLng(latitude, longitude);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(country, 10.0f));
                    googleMap.addMarker(new MarkerOptions().position(country).title(eventLocation));

                }
            } catch (IOException e) {
                e.printStackTrace();            }
        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Use reverse geocoding to find the address at the clicked location
                try {
                    List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);

                        // Extract the country name from the address
                        String countryName = address.getCountryName();

                        // Display the country name to the user (e.g., Toast message)
                        Toast.makeText(GoogleMapsActivity.this, "The selected country is " + countryName, Toast.LENGTH_LONG).show();
                    } else {
                        // Handle case where no address is found
                        Toast.makeText(GoogleMapsActivity.this, "No Country at this location!! Sorry", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    // Handle exceptions during reverse geocoding
                    e.printStackTrace();
                }
            }

        });

    }
}