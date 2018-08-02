package com.practice.project.android_bootcamp.utilities;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practice.project.android_bootcamp.MainActivity;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.JsonResponse;
import com.practice.project.android_bootcamp.model.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FourSquareAPIController implements Callback<JsonResponse>{

    private final String BASE_URL = "https://api.foursquare.com/v2/";
    private final String API_VERSION = "20130815";
    private final String RADIUS = "1000";

    private String mGeoLocation;
    private MutableLiveData<List<Venue>> mVenues;
    private Context mContext;
    private FileUtilities mFileUtilities;
    private String mClientID;
    private String mClientSecret;

    public FourSquareAPIController(String geoLocation, MutableLiveData<List<Venue>> venues, Context context) {
        mGeoLocation = geoLocation;
        mVenues = venues;
        mContext = context;

        mFileUtilities = new FileUtilities(mContext);
        mClientID = mFileUtilities.getFourSquareData("CLIENT_ID");
        mClientSecret = mFileUtilities.getFourSquareData("CLIENT_SECRET");
    }

    public void start() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FourSquareAPI fourSquareAPI = retrofit.create(FourSquareAPI.class);

        Call<JsonResponse> call = fourSquareAPI.requestSearch(mClientID, mClientSecret, API_VERSION, mGeoLocation, RADIUS);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
        try {
            MainActivity.sVenuesAppDatabase.clearAllTables();
        }catch (Exception e){}
        List<Venue> venues = response.body().getResponse().getVenues();
        for (Venue venue: venues) {
            try {
                if (venue.getCategories().size()==0){
                    Category category = new Category();
                    category.setId("Undefined");
                    category.setName("Undefined");
                    venue.getCategories().add(category);
                }
                int categoryId = (int) MainActivity.sVenuesAppDatabase.categoryDao()
                        .insert(venue.getCategories().get(0));
                venue.getLocation().setFormattedAddressString(venue.getLocation().getFormattedAddress().toString());
                int locationId = (int) MainActivity.sVenuesAppDatabase.locationDao()
                        .insert(venue.getLocation());

                venue.setCategoryId(categoryId);
                venue.setLocationId(locationId);

                int venueId = (int) MainActivity.sVenuesAppDatabase.venueDao().insert(venue);
                venue.setVenueId(venueId);
            }catch (Exception e){}
        }
        mVenues.setValue(venues);
    }

    @Override
    public void onFailure(Call<JsonResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
