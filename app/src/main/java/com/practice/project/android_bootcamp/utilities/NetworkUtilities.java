package com.practice.project.android_bootcamp.utilities;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.practice.project.android_bootcamp.MainActivity;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtilities {

    // This variables are used to do the Foursquare API request.
    private String mUrl;
    private static final String CLIENT_ID = "DJS3YIF02PXN1VHITVGRS3Q43X0XOUZ1R1QDLPCLF4ZYWYBI";
    private static final String CLIENT_SECRET = "XQUKOG2D00ZIQZ0OB4XNB3TEYGTVDRGDHS343IHVATP5GBEA";
    private static final int RADIUS = 1000;

    public NetworkUtilities() {
    }

    public boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getVenuesInLocation(MutableLiveData<List<Venue>> venues, Double latitude, Double longitude, Context context){
        MainActivity.mVenuesAppDatabase.clearAllTables();
        List<Venue> venuesList = new ArrayList<Venue>();
        mUrl = "https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815%20&ll=" + latitude + "," + longitude + "&radius=" + RADIUS;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray listVenuesOnResponse = null;
                        try {
                            listVenuesOnResponse = response.getJSONObject("response").getJSONArray("venues");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < listVenuesOnResponse.length(); i++) {
                            try {
                                Venue venue = new Venue();
                                //The name and location of the venue are obtained
                                venue.setName(listVenuesOnResponse.getJSONObject(i).getString("name"));
                                JSONObject location = listVenuesOnResponse.getJSONObject(i).getJSONObject("location");
                                venue.setLatitude(location.getDouble("lat"));
                                venue.setLongitude(location.getDouble("lng"));
                                //The formatted Address of the venue is obtained
                                JSONArray formattedAddress = location.getJSONArray("formattedAddress");
                                String formattedAddressString = "";
                                for (int j = 0; j < formattedAddress.length(); j++) {
                                    formattedAddressString += formattedAddress.getString(j) + ", ";
                                }
                                venue.setAddress(formattedAddressString);
                                ////The category name of the venue is obtained
                                JSONObject categories = listVenuesOnResponse.getJSONObject(i).getJSONArray("categories").getJSONObject(0);
                                Category category = new Category();
                                category.setName(categories.getString("name"));
                                //The category is stored in the database.
                                int categoryId = (int) MainActivity.mVenuesAppDatabase.categoryDao().insert(category);
                                //The category fields are completed with the generated id and the category is associated with the venue.
                                category.setId(categoryId);
                                venue.setCategoryId(category.getId());
                                venue.setCategory(category);
                                //The venue is stored in the database.
                                int venueId = (int) MainActivity.mVenuesAppDatabase.venueDao().insert(venue);
                                venue.setId(venueId);
                                //The list of venues of the view model is loaded
                                venuesList.add(venue);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        venues.setValue(venuesList);
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
