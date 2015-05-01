package com.fzv.glucowatch;

public class GlucoseInput extends InputLayout {


    @Override
    protected int getView() {
        return R.layout.activity_glucose_input;
    }

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
        return "mmol/L";
    }

    @Override
    protected String getShortUnit() {
        return "";
    }
}
