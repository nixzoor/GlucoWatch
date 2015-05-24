package com.fzv.glucowatch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fzv.glucowatch.R;

/**
 * Created by Nik on 7.5.2015.
 */
public class FoodInput extends Fragment{

    private Spinner mealSpinner;
    private Spinner carbohydratesSpinner;

    public String getMealValue() {
        return mealSpinner.getSelectedItem().toString();
    }

    public String getCarbohydratesValue() {
        return carbohydratesSpinner.getSelectedItem().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.food_input_fragment, container, false);
        componentInitialization(rootView);

        return rootView;
    }

    private void componentInitialization(ViewGroup rootView) {
        mealSpinner = (Spinner) rootView.findViewById(R.id.meal_size_spinner);
        setDataSpinner(mealSpinner , R.array.meal_size);
        carbohydratesSpinner = (Spinner) rootView.findViewById(R.id.meal_carbohydrates_spinner);
        setDataSpinner(carbohydratesSpinner , R.array.carbohydrant_procents);
    }

    void setDataSpinner(Spinner spinner, int stringArray){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),stringArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }
}
