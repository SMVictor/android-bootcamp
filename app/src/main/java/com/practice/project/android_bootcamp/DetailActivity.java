package com.practice.project.android_bootcamp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleVenueDetail;
    private TextView mAddressVenueDetail;
    private TextView mCategoryVenueDetail;
    private RideRequestButton mRequestButton;
    private Venue mVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


    }

    @NonNull
    private void loadVenueInformation() {

        mVenue = (Venue) getIntent().getSerializableExtra("Venue");

        mTitleVenueDetail.setText(mVenue.getName());
        mAddressVenueDetail.setText(mVenue.getLocation().getFormattedAddressString());

        String categoriesNames = "";

        for (Category category:mVenue.getCategories()) {

            categoriesNames+=category.getName()+" ";
        }
        mCategoryVenueDetail.setText(categoriesNames);
    }

    private void loadUberButtonInformation() {

        RideParameters rideParams = new RideParameters.Builder()
                .setDropoffLocation(mVenue.getLocation().getLat(), mVenue.getLocation().getLng(), mVenue.getName(), mVenue.getLocation().getFormattedAddressString())
                .build();

        mRequestButton.setRideParameters(rideParams);
        mRequestButton.setSession(MainActivity.sSession);
        try{
            mRequestButton.loadRideInformation();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
