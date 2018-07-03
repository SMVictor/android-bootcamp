package com.practice.project.android_bootcamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.practice.project.android_bootcamp.model.Venue;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitleVenueDetail;
    private TextView tvAddressVenueDetail;
    private TextView tvCategoryVenueDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitleVenueDetail = findViewById(R.id.tvTitleVenueDetail);
        tvAddressVenueDetail = findViewById(R.id.tvAddressVenueDetail);
        tvCategoryVenueDetail = findViewById(R.id.tvCategoryVenueDetail);

        Venue venue = (Venue) getIntent().getSerializableExtra("Venue");

        tvTitleVenueDetail.setText(venue.getName());
        tvAddressVenueDetail.setText(venue.getAddress());
        tvCategoryVenueDetail.setText(venue.getCategory().getName());
    }
}
