package com.practice.project.android_bootcamp;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.practice.project.android_bootcamp.data.VenueDatabase;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private LocationManager mLocManager;
    public static VenueDatabase sVenuesAppDatabase;
    public static MutableLiveData<List<Double>> sGeoLocation = new MutableLiveData<>();
    public static ServerTokenSession sSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.principal_tab);
        mViewPager = findViewById(R.id.principal_view_pager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        sVenuesAppDatabase = Room.databaseBuilder(getApplicationContext(), VenueDatabase.class, "venuesdb").allowMainThreadQueries().build();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        locationStart();
        createFragments();
        uberSDKInitialize();
    }

    private void uberSDKInitialize() {
        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId("toRSbNh4KmgO7YtHXe4fjRYOfQrfn6bO")
                .setServerToken("QO6Xhtm1jJkk1muoLeF2GAWDrKKV_oAmRRGYX_i9")
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();
        UberSdk.initialize(config);

        sSession = new ServerTokenSession(config);
    }

    private void createFragments() {
        mAdapter.addFragment(new FragmentList(), getString(R.string.venues_list_tap_title));
        mAdapter.addFragment(new FragmentMap(), getString(R.string.venues_map_tap_title));
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_local_activity);
    }

    /*
     * To get current location, a resource must be created outside the view model to obtain the venues
     * */
    private void locationStart()
    {
        //It is verified if the user grants the permissions necessary to use her/his current position.
        mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            Toast.makeText(this, "Please proceed to make your request again", Toast.LENGTH_LONG).show();
            return;
        }
        // It is initialized the LocationListener Manager
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                List<Double> geoLocation = new ArrayList<>();
                geoLocation.add(location.getLatitude());
                geoLocation.add(location.getLongitude());
                sGeoLocation.setValue(geoLocation);
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
