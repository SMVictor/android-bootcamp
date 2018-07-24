package com.practice.project.android_bootcamp.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.project.android_bootcamp.DetailActivity;
import com.practice.project.android_bootcamp.databinding.VenueBinding;
import com.practice.project.android_bootcamp.model.Venue;

import java.util.ArrayList;
import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueAdapterViewHolder> {

    private List<Venue> mVenuesData = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public VenuesAdapter() {
    }

    /**
     * Cache of the children views for a venue list item.
     */
    public class VenueAdapterViewHolder extends RecyclerView.ViewHolder {

        private VenueBinding venueBinding;
        public VenueAdapterViewHolder(VenueBinding venueBinding) {
            super(venueBinding.getRoot());
            this.venueBinding = venueBinding;
        }
        public void bind(Venue venue){
            this.venueBinding.setVenueModel(venue);
        }
    }
    @Override
    public VenueAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        VenueBinding venueBinding = VenueBinding.inflate(layoutInflater, viewGroup, false);

        VenueAdapterViewHolder venueAdapterViewHolder = new VenueAdapterViewHolder(venueBinding);

        venueAdapterViewHolder.itemView.setOnClickListener(view -> {
            Class destinationClass = DetailActivity.class;
            Intent intentToStartDetailActivity = new Intent(viewGroup.getContext(), destinationClass);
            intentToStartDetailActivity.putExtra("Venue", mVenuesData.get(venueAdapterViewHolder.getAdapterPosition()));
            viewGroup.getContext().startActivity(intentToStartDetailActivity);
        });

        return venueAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(VenueAdapterViewHolder venueAdapterViewHolder, int position) {
        Venue venue = mVenuesData.get(position);
        venueAdapterViewHolder.bind(venue);
    }

    @Override
    public int getItemCount() {
        if (null == mVenuesData) return 0;
        return mVenuesData.size();
    }

    public void setVenueData(List<Venue> venueData) {
        mVenuesData = venueData;
        notifyDataSetChanged();
    }
}