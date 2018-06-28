package com.practice.project.android_bootcamp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import com.practice.project.android_bootcamp.viewmodel.VenueViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {
    //Fragment view Variable
    private View view;
    private VenueViewModel model;
    // Venues List
    private List<String> mVenueData;
    // This variables are used to display the screen objects
    private RecyclerView mRecyclerView;
    private VenueAdapter mVenueAdapter;

    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_venues);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mVenueAdapter = new VenueAdapter();

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mVenueAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(VenueViewModel.class);
        model.setContext(getActivity());
        model.getVenues().observe(this, venues -> {
            mVenueAdapter.setVenueData(venues);
        });
    }
}
