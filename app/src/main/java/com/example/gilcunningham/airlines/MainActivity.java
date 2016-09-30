package com.example.gilcunningham.airlines;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.data.FavoriteAirlineInfo;
import com.example.gilcunningham.airlines.view.AirlinesViewPager;
import com.example.gilcunningham.airlines.view.adapter.PagerAdapter;
import com.example.gilcunningham.airlines.view.fragment.AllAirlinesFragment;
import com.example.gilcunningham.airlines.view.fragment.MyFavoritesFragment;

import java.util.ArrayList;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Display list of airlines sorted in alphabetic order, tabs to page between
 * all airlines and favorite airlines, and ability to get details on tap
 */

public class MainActivity extends AppCompatActivity {

    public static final String AIRLINES_EXTRA = "AIRLINES_EXTRA";

    private ArrayList<AirlineInfo> mAirlines;
    private Fragment mFavoritesFragment;
    private AirlinesViewPager mViewPager;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mAirlines = getIntent().getParcelableArrayListExtra(AIRLINES_EXTRA);
        } else {
            mAirlines = savedInstanceState.getParcelableArrayList(AIRLINES_EXTRA);
        }

        doActivityLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        registerReceiver(mFavoritesUpdatedReceiver, new IntentFilter(FavoriteAirlineInfo.FAVORITES_UPDATED_INTENT));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        unregisterReceiver(mFavoritesUpdatedReceiver);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(AIRLINES_EXTRA, mAirlines);
    }

    /** initial layout of views **/
    private void doActivityLayout() {

        setContentView(R.layout.activity_main);

        mAdapter = new PagerAdapter(getSupportFragmentManager());

        // all airlines list
        Fragment airlinesFragment = new AllAirlinesFragment();

        // set data
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AIRLINES_EXTRA, mAirlines);
        airlinesFragment.setArguments(bundle);
        mAdapter.addFragment(airlinesFragment, getResources().getString(R.string.tab_airlines));

        // favorite airlines list
        mFavoritesFragment = new MyFavoritesFragment();

        // set data
        ArrayList<AirlineInfo> favorites = FavoriteAirlineInfo.getInstance(this).getFavorites();
        bundle = new Bundle();
        bundle.putParcelableArrayList(AIRLINES_EXTRA, favorites);
        mFavoritesFragment.setArguments(bundle);
        mAdapter.addFragment(mFavoritesFragment, getResources().getString(R.string.tab_favorites));

        // set adapter of viewpager
        mViewPager = (AirlinesViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);

        // set viewpager on tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    // Receiver to listen for updates to favorites list
    private BroadcastReceiver mFavoritesUpdatedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            ArrayList<AirlineInfo> favorites = FavoriteAirlineInfo.getInstance(MainActivity.this).getFavorites();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AIRLINES_EXTRA, favorites);
            mFavoritesFragment.setArguments(bundle);

            // reset view pager to reconstruct fragments
            mViewPager.setAdapter(mAdapter);
        }
    };
}
