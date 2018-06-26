package com.practice.project.android_bootcamp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.principal_tab);
        viewPager = (ViewPager) findViewById(R.id.principal_view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentList(), getString(R.string.venues_list_tap_title));
        adapter.addFragment(new FragmentMap(), getString(R.string.venues_map_tap_title));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_local_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }
}
