package com.practice.project.android_bootcamp.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.*;

public class VenueViewModel extends ViewModel {

    private MutableLiveData<List<Venue>> venues;
    private Context context;
    private Activity activity;
    // This variables are used to do the Foursquare API request.
    private Double longitude;
    private Double latitude;
    private LocationManager mlocManager;
    private String url;
    private String CLIENT_ID = "DJS3YIF02PXN1VHITVGRS3Q43X0XOUZ1R1QDLPCLF4ZYWYBI";
    private String CLIENT_SECRET = "XQUKOG2D00ZIQZ0OB4XNB3TEYGTVDRGDHS343IHVATP5GBEA";
    private int RADIUS = 1000;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LiveData<List<Venue>> getVenues() {
        if (venues == null) {
            venues = new MutableLiveData<List<Venue>>();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                Toast.makeText(getContext(), "Please proceed to make your request again", Toast.LENGTH_LONG).show();
            }
            else
            {
                locationStart();
            }
        }
        return venues;
    }
    private void locationStart()
    {
        //It is verified if the user grants the permissions necessary to use her/his current position.
        mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getContext().startActivity(settingsIntent);
        }

        //It is verified if the gps is activated.
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
                loadVenues();
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
        //mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, locationListener);
    }

    private void loadVenues() {
        url = "https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815%20&ll=" + latitude + "," + longitude + "&radius=" + RADIUS;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Venue> list = new ArrayList<Venue>();

                        try {
                            JSONArray listVenuesOnResponse = response.getJSONObject("response").getJSONArray("venues");
                            for (int i = 0; i < listVenuesOnResponse.length(); i++) {

                                Venue venue = new Venue();
                                venue.setId(listVenuesOnResponse.getJSONObject(i).getString("id"));
                                venue.setName(listVenuesOnResponse.getJSONObject(i).getString("name"));
                                JSONObject location = listVenuesOnResponse.getJSONObject(i).getJSONObject("location");
                                venue.setLatitude(location.getDouble("lat"));
                                venue.setLongitude(location.getDouble("lng"));
                                JSONArray formattedAddress = location.getJSONArray("formattedAddress");
                                String formattedAddressString = "";
                                for (int j = 0; j < formattedAddress.length(); j++) {
                                    formattedAddressString += formattedAddress.getString(j) + ", ";
                                }
                                venue.setAddress(formattedAddressString);
                                JSONObject categories = listVenuesOnResponse.getJSONObject(i).getJSONArray("categories").getJSONObject(0);
                                Category category = new Category();
                                category.setId(categories.getString("id"));
                                category.setName(categories.getString("name"));
                                list.add(venue);
                            }
                            venues.setValue(list);
                        } catch (Exception e) {
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                requestQueue.stop();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
