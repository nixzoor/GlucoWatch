package com.fzv.glucowatch.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.fzv.glucowatch.R;
import com.fzv.glucowatch.adapter.ScreenSlidePagerAdapter;
import com.fzv.glucowatch.fragment.DataSendFragment;
import com.fzv.glucowatch.fragment.FoodInput;
import com.fzv.glucowatch.fragment.GlucoseInput;
import com.fzv.glucowatch.fragment.InsulinInput;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    List<Fragment> fragments;

    public List<Fragment> getFragments() {
        return fragments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);

    }

    private void initFragments() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new InsulinInput());
        fragments.add(new GlucoseInput());
        fragments.add(new FoodInput());
        fragments.add(new DataSendFragment());
    }

}
