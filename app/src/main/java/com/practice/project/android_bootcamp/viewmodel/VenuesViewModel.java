package com.practice.project.android_bootcamp.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.practice.project.android_bootcamp.MainActivity;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;
import com.practice.project.android_bootcamp.utilities.FourSquareAPIController;
import com.practice.project.android_bootcamp.utilities.NetworkUtilities;

import java.util.List;

public class VenuesViewModel extends ViewModel {

    private MutableLiveData<List<Venue>> mVenues;
    private Context mContext;
    private Activity mActivity;
    private Double mCurrentLongitude;
    private Double mCurrentLatitude;
    private LocationManager mLocManager;
    private NetworkUtilities mNetworkUtilities = new NetworkUtilities();

    ////////////////////////////////// Getters and Setters//////////////////////////////////////////
    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Double getLongitude() {
        return mCurrentLongitude;
    }

    public void setLongitude(Double longitude) {
        this.mCurrentLongitude = longitude;
    }

    public Double getLatitude() {
        return mCurrentLatitude;
    }

    public void setLatitude(Double latitude) {
        this.mCurrentLatitude = latitude;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public LiveData<List<Venue>> getVenues() {
        if (mVenues == null) {
            mVenues = new MutableLiveData<List<Venue>>();
            if(mNetworkUtilities.isConnectedToNetwork(getContext())){
                locationStart();
            }
            else {
                loadVenuesFromDatabase();
            }
        }
        return mVenues;
    }
    /*
    * To get current location, a resource must be created outside the view model to obtain the venues
    * */
    private void locationStart()
    {
        //It is verified if the user grants the permissions necessary to use her/his current position.
        mLocManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getContext().startActivity(settingsIntent);
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            Toast.makeText(getContext(), "Please proceed to make your request again", Toast.LENGTH_LONG).show();
            return;
        }
        // It is initialized the LocationListener Manager
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mCurrentLatitude = location.getLatitude();
                mCurrentLongitude = location.getLongitude();
                loadVenuesFromFourSquareAPI();
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

    private void loadVenuesFromFourSquareAPI() {
        String geoLocation = mCurrentLatitude+","+mCurrentLongitude;
        FourSquareAPIController fourSquareAPIController = new FourSquareAPIController();
        fourSquareAPIController.setmGeoLocation(geoLocation);
        fourSquareAPIController.setmVenues(mVenues);
        fourSquareAPIController.start();
    }

    private void loadVenuesFromDatabase() {
        try {
            List<Venue> venuesList = MainActivity.mVenuesAppDatabase.venueDao().getAll();
            List<Category> categoriesList = MainActivity.mVenuesAppDatabase.categoryDao().getAll();
            List<com.practice.project.android_bootcamp.model.Location> locationList = MainActivity.mVenuesAppDatabase.locationDao().getAll();
            if (venuesList.size() != 0){
                for (Venue venue:venuesList) {
                    for (Category category : categoriesList) {
                        if (venue.getCategoryId() == category.getCategoryId()){
                            venue.getCategories().add(category);
                            break;
                        }
                    }
                    for (com.practice.project.android_bootcamp.model.Location location : locationList) {
                        if (venue.getLocationId() == location.getLocationId()){
                            venue.setLocation(location);
                            break;
                        }
                    }
                }
                //The list of venues of the view model is loaded
                mVenues.setValue(venuesList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
