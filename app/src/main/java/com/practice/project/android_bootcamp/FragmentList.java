package com.practice.project.android_bootcamp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.project.android_bootcamp.model.Venue;
import com.practice.project.android_bootcamp.viewmodel.VenuesViewModel;

public class FragmentList extends Fragment {
    private VenuesViewModel mVenuesViewModel;
    // This variables are used to display the screen objects
    private RecyclerView mVenuesRecyclerView;
    private VenuesAdapter mVenuesAdapter;

    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        mVenuesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_venues);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mVenuesAdapter = new VenuesAdapter();

        mVenuesRecyclerView.setLayoutManager(layoutManager);
        mVenuesRecyclerView.setHasFixedSize(true);
        mVenuesRecyclerView.setAdapter(mVenuesAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVenuesViewModel = ViewModelProviders.of(getActivity()).get(VenuesViewModel.class);
        mVenuesViewModel.setActivity(getActivity());
        mVenuesViewModel.setContext(getContext());
        mVenuesViewModel.getVenues().observe(this, venues -> {
            mVenuesAdapter.setVenueData(venues);
        });
    }
}
