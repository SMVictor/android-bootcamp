package com.practice.project.android_bootcamp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.project.android_bootcamp.model.Venue;

import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueAdapterViewHolder> {

    private List<Venue> mVenueData;

    public VenueAdapter() {
    }

    /**
     * Cache of the children views for a venue list item.
     */
    public class VenueAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mVenueTextView;

        public VenueAdapterViewHolder(View view) {
            super(view);
            mVenueTextView = (TextView) view.findViewById(R.id.tv_venue_data);
        }
    }
    @Override
    public VenueAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.venue_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        VenueAdapterViewHolder venueAdapterViewHolder = new VenueAdapterViewHolder(view);

        venueAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = DetailActivity.class;
                Intent intentToStartDetailActivity = new Intent(context, destinationClass);
                intentToStartDetailActivity.putExtra("Venue", mVenueData.get(venueAdapterViewHolder.getAdapterPosition()));
                context.startActivity(intentToStartDetailActivity);
            }
        });
        return venueAdapterViewHolder;
}

    @Override
    public void onBindViewHolder(VenueAdapterViewHolder venueAdapterViewHolder, int position) {
        String venueForThisPosition = mVenueData.get(position).getName();
        venueAdapterViewHolder.mVenueTextView.setText(venueForThisPosition);
    }

    @Override
    public int getItemCount() {
        if (null == mVenueData) return 0;
        return mVenueData.size();
    }

    public void setVenueData(List<Venue> venueData) {
        mVenueData = venueData;
        notifyDataSetChanged();
    }
}