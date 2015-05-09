package com.fzv.glucowatch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Nik on 7.5.2015.
 */
public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

    private  List<Fragment> fragments;

    public ScreenSlidePagerAdapter(FragmentManager fm){
        super(fm);
    }

    public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
