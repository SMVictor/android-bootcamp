package com.practice.project.android_bootcamp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.*;

public class VenueViewModel extends ViewModel {

    private MutableLiveData<List<Venue>> venues;
    private Context context;
    // This variables are used to do the Foursquare API request.
    private Double longitude;
    private Double latitude;
    private String url;
    private String CLIENT_ID = "DJS3YIF02PXN1VHITVGRS3Q43X0XOUZ1R1QDLPCLF4ZYWYBI";
    private String CLIENT_SECRET = "XQUKOG2D00ZIQZ0OB4XNB3TEYGTVDRGDHS343IHVATP5GBEA";
    private int RADIUS = 250;

    public LiveData<List<Venue>> getVenues() {
        if (venues == null) {
            venues = new MutableLiveData<List<Venue>>();
            loadVenues();
        }
        return venues;
    }

    private void loadVenues() {
        longitude = 9.936421;
        latitude = -84.079701;
        url = "https://api.foursquare.com/v2/venues/search?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&v=20130815%20&ll="+longitude+","+latitude+"&radius="+RADIUS;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Venue> list = new ArrayList<Venue>();

                        try {
                            JSONArray listVenuesOnResponse = response.getJSONObject("response").getJSONArray("venues");
                            for (int i = 0; i<listVenuesOnResponse.length(); i++) {

                                Venue venue = new Venue();
                                venue.setId(listVenuesOnResponse.getJSONObject(i).getString("id"));
                                venue.setName(listVenuesOnResponse.getJSONObject(i).getString("name"));
                                JSONObject location = listVenuesOnResponse.getJSONObject(i).getJSONObject("location");
                                venue.setLatitude(location.getDouble("lat"));
                                venue.setLongitude(location.getDouble("lng"));
                                JSONArray formattedAddress = location.getJSONArray("formattedAddress");
                                String formattedAddressString = "";
                                for (int j = 0; j < formattedAddress.length(); j++){
                                formattedAddressString += formattedAddress.getString(j)+", ";
                               }
                                venue.setAddress(formattedAddressString);
                                JSONObject categories = listVenuesOnResponse.getJSONObject(i).getJSONArray("categories").getJSONObject(0);
                                Category category = new Category();
                                category.setId(categories.getString("id"));
                                category.setName(categories.getString("name"));
                                list.add(venue);
                            }
                            venues.setValue(list);
                        }
                        catch(Exception e){}
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
