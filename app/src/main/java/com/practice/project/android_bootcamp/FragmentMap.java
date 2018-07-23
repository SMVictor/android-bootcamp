package com.practice.project.android_bootcamp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.practice.project.android_bootcamp.model.Venue;
import com.practice.project.android_bootcamp.viewmodel.VenuesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentMap extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Venue> mVenues = new ArrayList<>();
    private VenuesViewModel mVenuesViewModel;

    public FragmentMap(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        getMapAsync(this);
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(marker -> {
            for (int i = 0; i< mVenues.size(); i++){
                if (mVenues.get(i).getVenueId() ==  (int) marker.getTag()){
                    Class destinationClass = DetailActivity.class;
                    Intent intentToStartDetailActivity = new Intent(getContext(), destinationClass);
                    intentToStartDetailActivity.putExtra("Venue", mVenues.get(i));
                    getContext().startActivity(intentToStartDetailActivity);
                }
            }
            return false;
        });

        MainActivity.sGeoLocation.observeForever(geoLocation ->
                setInitialLocationMap(geoLocation.get(0),geoLocation.get(1)));

        mVenuesViewModel = ViewModelProviders.of(getActivity()).get(VenuesViewModel.class);
        mVenuesViewModel.setContext(getContext());
        mVenuesViewModel.setActivity(getActivity());
        mVenuesViewModel.getVenues().observe(this, venues -> {
            mVenues = venues;
            setPointsInMap();
        });
    }
    public void setInitialLocationMap(Double latitude, Double longitude)
    {
        LatLng initialPosition = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(initialPosition)
                .title("Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag(-1);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition,16));
    }
    public void setPointsInMap()
    {
        for(int i = 0; i < mVenues.size(); i++)
        {
            mMap.addMarker(new MarkerOptions()
                    .title(mVenues.get(i).getName())
                    .position(new LatLng(mVenues.get(i).getLocation().getLat(), mVenues.get(i).getLocation().getLng())))
                    .setTag(mVenues.get(i).getVenueId());
        }
    }
}
