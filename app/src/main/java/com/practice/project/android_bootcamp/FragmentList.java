package com.practice.project.android_bootcamp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {

    //Fragment view Variable
    private View view;
    // Venues List
    private List<String> mVenueData;
    // This variables are used to display the screen objects
    private RecyclerView mRecyclerView;
    private VenueAdapter mVenueAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    // This variables are used to do the Foursquare API request.
    private Double longitude;
    private Double latitude;
    private String url;
    private String CLIENT_ID = "DJS3YIF02PXN1VHITVGRS3Q43X0XOUZ1R1QDLPCLF4ZYWYBI";
    private String CLIENT_SECRET = "XQUKOG2D00ZIQZ0OB4XNB3TEYGTVDRGDHS343IHVATP5GBEA";
    private int RADIUS = 250;

    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_venues);
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);
        mVenueAdapter = new VenueAdapter();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mVenueAdapter);
        loadVenueData();
        return view;
    }

    private void loadVenueData() {
        longitude = 9.936421;
        latitude = -84.079701;
        url = "https://api.foursquare.com/v2/venues/search?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&v=20130815%20&ll="+longitude+","+latitude+"&radius="+RADIUS;
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showVenueDataView();
                        mVenueData = createVenuesList(response);
                        mVenueAdapter.setVenueData(mVenueData);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorMessage();
                error.printStackTrace();
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void showVenueDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the venue data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    private List<String> createVenuesList(String response) {

        List<String> list = new ArrayList<>();
        list.add(response);
        return  list;
    }
}
