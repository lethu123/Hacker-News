package com.example.appnews;

import android.os.Bundle;

import com.example.appnews.presentation.favourite.FavouriteFragment;
import com.example.appnews.presentation.history.HistoryFragment;
import com.example.appnews.presentation.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.View;
import android.view.MenuItem;

import java.util.Stack;

import tool.compet.appbundle.arch.DkFragment;
import tool.compet.appbundle.arch.DkSimpleActivity;
import tool.compet.appbundle.binder.DkBinder;
import tool.compet.appbundle.binder.annotation.DkBindView;

public class MainActivity extends DkSimpleActivity {
    @DkBindView(R.id.navigation) BottomNavigationView actionBar;
    @DkBindView(R.id.toolbar) Toolbar toolbar;
    //@DkBindView(R.id.viewpager) ViewPager viewpager;

    //FragmentPagerAdapter adapterViewPager;
    private Stack<Fragment> stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View layout = View.inflate(this, R.layout.activity_main, null);
        setContentView(layout);
        DkBinder.bindViews(this, layout);

        //connectDB();

        toolbar.setTitle("Top Stories");
        setSupportActionBar(toolbar);

        //loadFragment(new HomeFragment());
        //adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        //viewpager.setAdapter(adapterViewPager);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        actionBar.setSelectedItemId(R.id.navigation_hot);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            DkFragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_hot:
                    toolbar.setTitle("Top Stories");
                    //viewpager.setCurrentItem(0);
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_edu:
                    toolbar.setTitle("History");
                    //viewpager.setCurrentItem(1);
                    fragment = new HistoryFragment();
                    break;
                case R.id.navigation_entertaiment:
                    toolbar.setTitle("Favourite");
                    //viewpager.setCurrentItem(2);
                    fragment = new FavouriteFragment();
                    break;
//                case R.id.new_detail_webview:
//                    toolbar.setTitle("abc");
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            //return true;
            return loadFragment(fragment);
        }
    };

    @Override
    public void onBackPressed() {
        if (!getChildNavigator().onBackPressed()) {
            super.onBackPressed();
        }
    }

    private boolean loadFragment(DkFragment fragment) {
        //switching fragment
        if (fragment != null) {
            getChildNavigator()
                    .beginTransaction()
                    //.removeAllAfter(fragment)
                    //.addIfAbsent(fragment)
                    .replaceAll(fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public int layoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public int fragmentContainerId() {
        return R.id.frame_container;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new HomeFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new  HistoryFragment();
                default:
                    return new FavouriteFragment();
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
