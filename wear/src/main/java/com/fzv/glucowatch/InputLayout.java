package com.fzv.glucowatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created abstract class for input layout.
 * Created by Nik on 1.5.2015.
 */
public abstract class InputLayout extends Activity {

    protected Double currentValue;
    private TextView displayer;

//    private DismissOverlayView mDismissOverlay;
//    private GestureDetector mDetector;

    abstract protected int getView();

    abstract protected Double getBottomValue();

    abstract protected Double getMiddleValue();

    abstract protected Double getUpperValue();

    abstract protected String getUnit();

    abstract protected String getShortUnit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        currentValue = 0.0;
        ((TextView)findViewById(R.id.unit_textViev)).setText(getUnit());
        displayer = (TextView) findViewById(R.id.value_textView);

        //Need to add click listener because in other case we can
        //click component that is under layout
        findViewById(R.id.layout_display).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nothing to do
            }
        });

        initializationComponents();
    }

    private void initializationComponents() {
        setButtonSpec((Button) findViewById(R.id.plus_x1),  getUpperValue());
        setButtonSpec((Button) findViewById(R.id.minus_x1),  -getUpperValue());

        setButtonSpec((Button) findViewById(R.id.plus_x2),  getMiddleValue());
        setButtonSpec((Button) findViewById(R.id.minus_x2), -getMiddleValue());

        setButtonSpec((Button) findViewById(R.id.plus_x3),  getBottomValue());
        setButtonSpec((Button) findViewById(R.id.minus_x3),  -getBottomValue());
    }

    //Converter for double numbers
    //We cut zero at the end
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
                    displayer.setText(convertToString(currentValue));
                }
            }
        });
    }


}
