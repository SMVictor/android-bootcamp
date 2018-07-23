package com.practice.project.android_bootcamp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.project.android_bootcamp.databinding.ListFragmentBinding;
import com.practice.project.android_bootcamp.viewmodel.RecyclerViewViewModel;

public class FragmentList extends Fragment {
    private RecyclerViewViewModel recyclerViewViewModel;

    public FragmentList(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ListFragmentBinding binding = ListFragmentBinding.bind(view);
        recyclerViewViewModel = new RecyclerViewViewModel(getActivity(), getContext());
        binding.setViewModel(recyclerViewViewModel);
        return view;
    }
}
