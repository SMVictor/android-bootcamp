package com.practice.project.android_bootcamp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentsList = new ArrayList<>();
    private List<String> mTitlesList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mTitlesList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlesList.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentsList.add(fragment);
        mTitlesList.add(title);
    }
}
