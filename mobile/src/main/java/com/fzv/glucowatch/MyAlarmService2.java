package com.fzv.glucowatch;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fzv.glucowatch.Utils.WearableUtils;

public class MyAlarmService2 extends Service {
    public MyAlarmService2() {
    }


    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {

        WearableUtils.showNotificationWithConfirmation(getApplicationContext(), "Opozorilo", "Ste ze pojedli kosilo?");
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
