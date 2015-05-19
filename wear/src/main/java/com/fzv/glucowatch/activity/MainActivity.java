package com.fzv.glucowatch.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.fzv.glucowatch.R;
import com.fzv.glucowatch.adapter.ScreenSlidePagerAdapter;
import com.fzv.glucowatch.fragment.DataSendFragment;
import com.fzv.glucowatch.fragment.FoodInput;
import com.fzv.glucowatch.fragment.GlucoseInput;
import com.fzv.glucowatch.fragment.InsulinInput;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private DismissOverlayView dismissOverlayView;
    private GestureDetector mDetector;

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
        initDissmiser();


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public boolean dispatchTouchEvent (MotionEvent ev){
        return mDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    private void initDissmiser() {
        // Obtain the DismissOverlayView element
        dismissOverlayView = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        dismissOverlayView.setIntroText("Long press");
        dismissOverlayView.showIntroIfNecessary();

        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent ev) {
                dismissOverlayView.show();
            }
        });
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new InsulinInput());
        fragments.add(new GlucoseInput());
        fragments.add(new FoodInput());
        fragments.add(new DataSendFragment());
    }

}
