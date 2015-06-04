package com.fzv.glucowatch.fragment;


import android.widget.ImageView;

import com.fzv.glucowatch.R;

public class InsulinInput extends AbstractInputLayout {

    @Override
    protected Double getBottomValue() {
        return 0.1;
    }

    @Override
    protected Double getMiddleValue() {
        return 1.0;
    }

    @Override
    protected Double getUpperValue() {
        return 5.0;
    }

    @Override
    protected String getUnit() {
        return "E";
    }

    @Override
    protected String getShortUnit() {
        return "";
    }

    @Override
    protected void setImageIcon() {
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.insulin_icon);
        imageView.getLayoutParams().height = 35;
        imageView.getLayoutParams().width = 90;
    }


}
