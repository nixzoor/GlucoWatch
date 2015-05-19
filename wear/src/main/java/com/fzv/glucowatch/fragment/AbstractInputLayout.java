package com.fzv.glucowatch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fzv.glucowatch.R;

import java.math.RoundingMode;

/**
 * Created abstract class for input layout.
 * Created by Nik on 1.5.2015.
 */
public abstract class AbstractInputLayout extends Fragment {

    protected Double currentValue;
    private TextView displayer;
    protected ViewGroup rootView;

    abstract protected Double getBottomValue();

    abstract protected Double getMiddleValue();

    abstract protected Double getUpperValue();

    abstract protected String getUnit();

    abstract protected String getShortUnit();

    protected abstract void setImageIcon();


    public Double getCurrentValue() {
        return currentValue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.abstract_input_layout, container, false);
        initFragment();
        return rootView;
    }

    private void initFragment() {
        currentValue = 0.0;
        displayer = (TextView) rootView.findViewById(R.id.value_display_textView);
        setDisplayText();
        setImageIcon();

        //Need to add click listener because in other case we can
        //click component that is under layout
        rootView.findViewById(R.id.layout_display).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nothing to do
            }
        });

        initializationComponents();
    }


    private void initializationComponents() {
        setButtonSpec((Button) rootView.findViewById(R.id.plus_x1),  getUpperValue());
        setButtonSpec((Button) rootView.findViewById(R.id.minus_x1),  -getUpperValue());

        setButtonSpec((Button) rootView.findViewById(R.id.plus_x2),  getMiddleValue());
        setButtonSpec((Button) rootView.findViewById(R.id.minus_x2), -getMiddleValue());

        setButtonSpec((Button) rootView.findViewById(R.id.plus_x3),  getBottomValue());
        setButtonSpec((Button) rootView.findViewById(R.id.minus_x3),  -getBottomValue());
    }

    //Converter for double numbers
    public static String convertToString(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    //Method add click listener for increasing and decreasing value
    // and add caption to button
    private void setButtonSpec(Button button,  final Double value) {
        if(value > 0)
            button.setText("+" + convertToString(value) + getShortUnit());
        else
            button.setText(convertToString(value) + getShortUnit());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentValue + value >= 0) {
                    currentValue += value;
                    //getting some strange behaviour so we need to round number to one decimal
                    currentValue = (double) Math.round(currentValue * 10) / 10;
                    setDisplayText();
                }
            }
        });
    }

    private void setDisplayText() {
        displayer.setText(convertToString(currentValue) + " " + getUnit());
    }


}
