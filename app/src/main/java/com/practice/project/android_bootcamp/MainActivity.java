package com.practice.project.android_bootcamp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.practice.project.android_bootcamp.data.VenueDatabase;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    public static VenueDatabase mVenuesAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.principal_tab);
        mViewPager = (ViewPager) findViewById(R.id.principal_view_pager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mVenuesAppDatabase = Room.databaseBuilder(getApplicationContext(), VenueDatabase.class, "venuesdb").allowMainThreadQueries().build();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        createFragments();
    }

    private void createFragments() {
        mAdapter.addFragment(new FragmentList(), getString(R.string.venues_list_tap_title));
        mAdapter.addFragment(new FragmentMap(), getString(R.string.venues_map_tap_title));
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_local_activity);
    }
}
