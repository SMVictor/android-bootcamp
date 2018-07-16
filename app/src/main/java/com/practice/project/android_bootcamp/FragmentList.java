package com.practice.project.android_bootcamp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.project.android_bootcamp.databinding.ListFragmentBinding;
import com.practice.project.android_bootcamp.viewmodel.RecyclerViewViewModel;
import com.practice.project.android_bootcamp.viewmodel.VenuesViewModel;

public class FragmentList extends Fragment {
    private VenuesViewModel mVenuesViewModel;
    private RecyclerViewViewModel recyclerViewViewModel;

    public FragmentList(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVenuesViewModel = ViewModelProviders.of(getActivity()).get(VenuesViewModel.class);
        mVenuesViewModel.setActivity(getActivity());
        mVenuesViewModel.setContext(getContext());
        mVenuesViewModel.getVenues().observe(this, venues -> {
            recyclerViewViewModel.setVenues(venues);

        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ListFragmentBinding binding = ListFragmentBinding.bind(view);
        recyclerViewViewModel = new RecyclerViewViewModel(getActivity());
        binding.setViewModel(recyclerViewViewModel);
        return view;
    }
}
