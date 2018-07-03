package com.practice.project.android_bootcamp;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.practice.project.android_bootcamp.model.Venue;
import com.practice.project.android_bootcamp.viewmodel.VenueViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentMap extends SupportMapFragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;
    private String urlAPIDirections;
    private List<Venue> venues;
    private VenueViewModel model;
    private Double latitude;
    private Double longitude;
    private LocationManager mlocManager;

    public FragmentMap(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = super.onCreateView(inflater, container, savedInstanceState);
        venues = new ArrayList<Venue>();
        getMapAsync(this);
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i=0; i<venues.size(); i++){
                    if (venues.get(i).getId().equalsIgnoreCase((String) marker.getTag())){
                        Class destinationClass = DetailActivity.class;
                        Intent intentToStartDetailActivity = new Intent(getContext(), destinationClass);
                        intentToStartDetailActivity.putExtra("Venue", venues.get(i));
                        getContext().startActivity(intentToStartDetailActivity);
                    }
                }
                return false;
            }//Fin del método onMarkerClick
        });

        locationStart();

        model = ViewModelProviders.of(getActivity()).get(VenueViewModel.class);
        model.setContext(getContext());
        model.setActivity(getActivity());
        model.getVenues().observe(this, venues -> {
            this.venues = venues;
            setPointsInMap();
        });
    }
    public void setInitialLocationMap()
    {
        LatLng initialPosition = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(initialPosition)
                .title("Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag("Current Location");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition,16));
    }
    public void setPointsInMap()
    {
        if(venues.size() > 1)
        {
            this.urlAPIDirections += "&waypoints=";

            for(int i = 0; i < venues.size(); i++)
            {
                this.mMap.addMarker(new MarkerOptions()
                        .title(venues.get(i).getName())
                        .position(new LatLng(venues.get(i).getLatitude(),venues.get(i).getLongitude())))
                        .setTag(venues.get(i).getId());
                this.urlAPIDirections += venues.get(i).getLatitude() + "," + venues.get(i).getLongitude();
                if(i != (venues.size()-1)){this.urlAPIDirections += "|";}
            }
        }
        else
        {
            this.mMap.addMarker(new MarkerOptions()
                    .title(venues.get(0).getName())
                    .position(new LatLng(venues.get(0).getLatitude(),venues.get(0).getLongitude())))
                    .setTag(venues.get(0).getId());
        }
        this.urlAPIDirections += "&key=AIzaSyAftKCowaKEkGSvRLV0ZrANIlDDKAOaQgc";
    }
    private void locationStart()
    {
        //It is verified if the user grants the permissions necessary to use her/his current position.
        mlocManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getContext().startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        // It is initialized the LocationListener Manager
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                setInitialLocationMap();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }
}
