package com.fzv.glucowatch;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.fzv.glucowatch.Utils.WearableUtils;

public class MyAlarmService3 extends Service {
    public MyAlarmService3() {
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
        WearableUtils.showNotificationWithConfirmation(getApplicationContext(), "Opozorilo", "Potrebno bo pojesti večerjo");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
