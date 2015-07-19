package com.fzv.glucowatch.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.fzv.glucowatch.DB_Handler;
import com.fzv.glucowatch.Meritev;
import com.fzv.glucowatch.Obrok;
import com.fzv.glucowatch.VnosZdravila;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.HashMap;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

/**
 * Created by Denis on 17.7.2015.
 */
public class PebbleService extends Service {

    private final static UUID PEBBLE_APP_UUID = UUID.fromString("673a7a3e-a201-4fc1-a51f-f46a7fe13266");

    public static Context mContext;

    boolean connected;
    final Handler handler = new Handler();
    private DB_Handler dbHandler;

    public PebbleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {

        mContext = this;

        connected = PebbleKit.isWatchConnected(getApplicationContext());
        dbHandler = new DB_Handler(this, null, null, 1);

        PebbleKit.registerReceivedDataHandler(this, new PebbleKit.PebbleDataReceiver(PEBBLE_APP_UUID) {

            @Override
            public void receiveData(Context arg0, int arg1, PebbleDictionary arg2) {
                //	Log.i(getLocalClassName(), "Received value=" + arg2.getInteger(1) + " for key: 0");

                final Integer insulinValue=(int) (long) arg2.getInteger(0);  //ničla je key od dictionary
                final Integer glucoseValue=(int) (long)arg2.getInteger(1);
                final Integer mealSize=(int) (long) arg2.getInteger(2);
                final Integer ohValue=(int) (long) arg2.getInteger(3);
                PebbleKit.sendAckToPebble(getApplicationContext(), 1);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //test(value, value1, value2, value3);
                        saveFoodInput(mealSize, ohValue);
                        saveGlucoseValue(glucoseValue);
                        saveInsulinValue(insulinValue);
                    }
                });

            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
       // Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }


    private void saveFoodInput(int meal_size, int oh_value) {
        String formatedDate = getFormatedDate();
        String meal = "";

        if(meal_size == 0)
            meal = "majhen";
        else if (meal_size==1)
            meal = "srednji";
        else if (meal_size==2)
            meal = "velik";

        Obrok o = new Obrok(formatedDate ,meal, oh_value);
        dbHandler.dodajObrok(o);

    }

    private void saveGlucoseValue(int glucose_value){
        String formatedDate = getFormatedDate();

        Meritev m = new Meritev(formatedDate, (double)glucose_value);
        Toast.makeText(getApplicationContext(), "Meritev dodana", Toast.LENGTH_SHORT).show();
        dbHandler.dodajMeritev(m);
    }

    private void saveInsulinValue(int insulin_value){
        String formatedDate = getFormatedDate();

        VnosZdravila insulin = new VnosZdravila(formatedDate, "Insulin" ,insulin_value);
        Toast.makeText(getApplicationContext(), "Meritev dodana", Toast.LENGTH_SHORT).show();
        dbHandler.dodajVnosZdravila(insulin);
    }

    private String getFormatedDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        return dateFormat.format(date);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }
}
