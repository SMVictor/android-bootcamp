package com.practice.project.android_bootcamp.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.practice.project.android_bootcamp.MainActivity;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;
import com.practice.project.android_bootcamp.utilities.FourSquareAPIController;
import com.practice.project.android_bootcamp.utilities.NetworkUtilities;

import java.util.ArrayList;
import java.util.List;

public class VenuesViewModel extends ViewModel {

    private MutableLiveData<List<Venue>> mVenues;
    private Context mContext;
    private NetworkUtilities mNetworkUtilities = new NetworkUtilities();


    public void setActivity(Activity activity) {
        Activity mActivity = activity;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }


    public LiveData<List<Venue>> getVenues() {
        if (mVenues == null) {
            mVenues = new MutableLiveData<>();
            if(mNetworkUtilities.isConnectedToNetwork(getContext())){
                MainActivity.mGeoLocation.observeForever(geoLocation -> loadVenuesFromFourSquareAPI(geoLocation.get(0)+","+geoLocation.get(1)));
            }
            else {
                new LoadVenuesFromDatabaseTask().execute();
            }
        }
        return mVenues;
    }

    private void loadVenuesFromFourSquareAPI(String geoLocation) {
        FourSquareAPIController fourSquareAPIController = new FourSquareAPIController();
        fourSquareAPIController.setGeoLocation(geoLocation);
        fourSquareAPIController.setVenues(mVenues);
        fourSquareAPIController.start();
    }

    public class LoadVenuesFromDatabaseTask extends AsyncTask<Void, Void, List<Venue>>{
        @Override
        protected List<Venue> doInBackground(Void... voids) {
            List<Venue> venues = new ArrayList<>();
            try {
                venues = MainActivity.mVenuesAppDatabase.venueDao().getAll();
                List<Category> categoriesList = MainActivity.mVenuesAppDatabase.categoryDao().getAll();
                List<com.practice.project.android_bootcamp.model.Location> locationList = MainActivity.mVenuesAppDatabase.locationDao().getAll();
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
