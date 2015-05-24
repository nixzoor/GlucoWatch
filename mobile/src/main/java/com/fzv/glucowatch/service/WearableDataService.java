package com.fzv.glucowatch.service;

import android.widget.Toast;

import com.fzv.glucowatch.DB_Handler;
import com.fzv.glucowatch.Meritev;
import com.fzv.glucowatch.Obrok;
import com.fzv.glucowatch.VnosZdravila;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nik on 13.5.2015.
 */
public class WearableDataService extends WearableListenerService {

    private DB_Handler dbHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHandler = new DB_Handler(this, null, null, 1);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String data = "";
        //convert data from bytes
        try {
            data = new String(messageEvent.getData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(messageEvent.getPath().equals("/glucose"))
            saveGlucoseValue(data);
        else if(messageEvent.getPath().equals("/insulin"))
            saveInsulinValue(data);
        else if(messageEvent.getPath().equals("/food"))
            saveFoodInput(data);
    }

    private void saveFoodInput(String data) {
        String formatedDate = getFormatedDate();
        String[] splitData = data.split(";");

        Obrok o = new Obrok(formatedDate ,splitData[0], Integer.parseInt(splitData[1].split("%")[0]));
        dbHandler.dodajObrok(o);

    }

    private void saveGlucoseValue(String value){
        String formatedDate = getFormatedDate();

        Meritev m = new Meritev(formatedDate, Double.parseDouble(value));
        Toast.makeText(getApplicationContext(), "Meritev dodana", Toast.LENGTH_SHORT).show();
        dbHandler.dodajMeritev(m);
    }

    private void saveInsulinValue(String value){
        String formatedDate = getFormatedDate();
        String[] splitByPoint = value.split("\\.");

        VnosZdravila insulin = new VnosZdravila(formatedDate, "Insulin" ,Integer.parseInt(splitByPoint[0]));
        Toast.makeText(getApplicationContext(), "Meritev dodana", Toast.LENGTH_SHORT).show();
        dbHandler.dodajVnosZdravila(insulin);
    }

    private String getFormatedDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        return dateFormat.format(date);
    }

}
