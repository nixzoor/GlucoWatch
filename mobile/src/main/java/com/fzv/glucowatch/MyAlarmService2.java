package com.fzv.glucowatch;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

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
        Toast.makeText(getApplicationContext(), "ALARM ZAGNAN! 2",
                Toast.LENGTH_SHORT).show();
        WearableUtils.showNotificationWithConfirmation(getApplicationContext(), "Opozorilo", "Potrebno bo pojesti kosilo");
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
