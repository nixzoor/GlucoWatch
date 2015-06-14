package com.fzv.glucowatch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import com.fzv.glucowatch.Utils.WearableUtils;

import java.util.Calendar;


public class MyAlarmService extends Service {
    public MyAlarmService() {
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
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        CasObrokov[] cas = dbHandler.vrniCaseObrokov();

        WearableUtils.showNotificationWithConfirmation(getApplicationContext(), "Opozorilo", "Ne pozabite na svoje glavne obroke\nZajtrk: " + cas[0].getCasObroka() + "\nKosilo: " + cas[1].getCasObroka() + "\nVecerja" + cas[2].getCasObroka());
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
