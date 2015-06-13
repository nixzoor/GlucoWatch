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
    public void onStart(Intent intent, int startId)
    {
        Toast.makeText(getApplicationContext(), "Alarm Zagnan 3!",
                Toast.LENGTH_SHORT).show();
        WearableUtils.showNotificationWithConfirmation(getApplicationContext(), "Opozorilo", "Ste ze pojedli večerjo? :)");
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
