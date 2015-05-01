package com.fzv.glucowatch;


public class FoodInput extends InputLayout {

    @Override
    protected int getView() {
        return R.layout.activity_food_input;
    }

    @Override
    protected Double getBottomValue() {
        return 1.0;
    }

    @Override
    protected Double getMiddleValue() {
        return 10.0;
    }

    @Override
    protected Double getUpperValue() {
        return 50.0;
    }

    @Override
    protected String getUnit() {
        return "grams";
    }

    @Override
    protected String getShortUnit() {
        return "g";
    }

}
