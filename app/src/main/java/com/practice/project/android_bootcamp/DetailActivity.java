package com.practice.project.android_bootcamp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleVenueDetail;
    private TextView mAddressVenueDetail;
    private TextView mCategoryVenueDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleVenueDetail = findViewById(R.id.tvTitleVenueDetail);
        mAddressVenueDetail = findViewById(R.id.tvAddressVenueDetail);
        mCategoryVenueDetail = findViewById(R.id.tvCategoryVenueDetail);

        Venue venue = (Venue) getIntent().getSerializableExtra("Venue");

        mTitleVenueDetail.setText(venue.getName());
        mAddressVenueDetail.setText(venue.getLocation().getFormattedAddressString());
        String categoriesNames = "";
        for (Category category:venue.getCategories()) {

            categoriesNames+=category.getName()+" ";
        }
        mCategoryVenueDetail.setText(categoriesNames);
    }
}
