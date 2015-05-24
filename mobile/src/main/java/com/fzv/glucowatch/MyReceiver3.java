package com.fzv.glucowatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver3 extends BroadcastReceiver {
    public MyReceiver3() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent service1 = new Intent(context, MyAlarmService3.class);
        context.startService(service1);
    }
}
