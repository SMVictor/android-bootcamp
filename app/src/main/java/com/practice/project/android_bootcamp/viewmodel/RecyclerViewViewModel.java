package com.practice.project.android_bootcamp.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.practice.project.android_bootcamp.MainActivity;
import com.practice.project.android_bootcamp.adapter.VenuesAdapter;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;
import com.practice.project.android_bootcamp.utilities.FourSquareAPIController;
import com.practice.project.android_bootcamp.utilities.NetworkUtilities;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewViewModel extends ViewModel {

    private VenuesAdapter mVenuesAdapter = new VenuesAdapter();
    private MutableLiveData<List<Venue>> mVenues = new MutableLiveData<>();
    private Activity mActivity;
    private Context mContext;
    private NetworkUtilities mNetworkUtilities = new NetworkUtilities();

    public RecyclerViewViewModel(Activity activity, Context context) {
        mActivity = activity;
        mContext = context;
        mVenues.observeForever(venues -> mVenuesAdapter.setVenueData(venues));
    }
    public final void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mVenuesAdapter);
        if(mNetworkUtilities.isConnectedToNetwork(mContext)){
            MainActivity.sGeoLocation.observeForever(geoLocation ->
                    loadVenues(geoLocation.get(0)+","+geoLocation.get(1))
            );
        }
        else {
            new LoadVenuesFromDatabaseTask().execute();
        }
    }

    private void loadVenues(String geoLocation) {
        FourSquareAPIController fourSquareAPIController = new FourSquareAPIController();
        fourSquareAPIController.setGeoLocation(geoLocation);
        fourSquareAPIController.setVenues(mVenues);
        fourSquareAPIController.start();
    }

    public class LoadVenuesFromDatabaseTask extends AsyncTask<Void, Void, List<Venue>> {
        @Override
        protected List<Venue> doInBackground(Void... voids) {
            List<Venue> venues = new ArrayList<>();
            try {
                venues = MainActivity.sVenuesAppDatabase.venueDao().getAll();
                List<Category> categoriesList = MainActivity.sVenuesAppDatabase.categoryDao().getAll();
                List<com.practice.project.android_bootcamp.model.Location> locationList = MainActivity.sVenuesAppDatabase.locationDao().getAll();
                if (venues.size() != 0){
                    for (Venue venue:venues) {
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
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return venues;
        }

        @Override
        protected void onPostExecute(List<Venue> venues) {
            super.onPostExecute(venues);
            mVenues.setValue(venues);
        }
    }
}
