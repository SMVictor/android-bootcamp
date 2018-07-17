package com.practice.project.android_bootcamp.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.practice.project.android_bootcamp.VenuesAdapter;
import com.practice.project.android_bootcamp.model.Venue;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewViewModel extends ViewModel {

    private VenuesAdapter mVenuesAdapter;
    private List<Venue> mVenues;
    private Activity mActivity;

    public RecyclerViewViewModel(Activity activity) {
        mVenues = new ArrayList<>();
        mVenuesAdapter = new VenuesAdapter();
        mActivity = activity;
    }
    public final void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mVenuesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        mVenuesAdapter.setVenueData(mVenues);
        loadVenues();
    }

    /**
     * Cargar Venues
     */
    private void loadVenues() {
    }

    public void setVenues(List<Venue> venues) {
        this.mVenues = venues;
        mVenuesAdapter.setVenueData(mVenues);
    }
}
