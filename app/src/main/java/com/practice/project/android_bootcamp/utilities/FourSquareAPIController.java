package com.practice.project.android_bootcamp.utilities;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practice.project.android_bootcamp.model.JsonResponse;
import com.practice.project.android_bootcamp.model.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FourSquareAPIController implements Callback<JsonResponse>{

    private static final String BASE_URL = "https://api.foursquare.com/v2/";
    private static final String CLIENT_ID = "DJS3YIF02PXN1VHITVGRS3Q43X0XOUZ1R1QDLPCLF4ZYWYBI";
    private static final String CLIENT_SECRET = "XQUKOG2D00ZIQZ0OB4XNB3TEYGTVDRGDHS343IHVATP5GBEA";
    private static final String API_VERSION = "20130815";
    private static final String RADIUS = "1000";
    private String mGeoLocation;
    private MutableLiveData<List<Venue>> mVenues;

    public void start() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FourSquareAPI fourSquareAPI = retrofit.create(FourSquareAPI.class);

        Call<JsonResponse> call = fourSquareAPI.requestSearch(CLIENT_ID, CLIENT_SECRET, API_VERSION, mGeoLocation, RADIUS);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
        mVenues.setValue(response.body().getResponse().getVenues());
    }

    @Override
    public void onFailure(Call<JsonResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public String getmGeoLocation() {
        return mGeoLocation;
    }

    public void setmGeoLocation(String mGeoLocation) {
        this.mGeoLocation = mGeoLocation;
    }

    public MutableLiveData<List<Venue>> getmVenues() {
        return mVenues;
    }

    public void setmVenues(MutableLiveData<List<Venue>> mVenues) {
        this.mVenues = mVenues;
    }
}
