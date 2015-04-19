package com.fzv.glucowatch.Utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.fzv.glucowatch.MainActivity;
import com.fzv.glucowatch.R;

/**
 * Class for handling events on andorid wear.
 *
 * Created by Nik on 19.4.2015.
 */
public class WearableUtils {

    // Show notifications on handheld device and wearable device.
    public static void showNotificationWithConfirmation(Context context,String notificationTitle,String notificationContent){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.confirm_icon,"Potrdi",pendingIntent).build();
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.abc_ab_solid_dark_holo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .extend(new NotificationCompat.WearableExtender().addAction(action))
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(001,notification);
    }
}
