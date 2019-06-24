package com.example.homework3;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(context, AddModifyGlucoseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context
                , 0, i,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentIntent(pendingIntent).setSmallIcon(android.R.drawable.btn_star).setContentTitle("title").setContentText("some text").setAutoCancel(true);
        builder.setChannelId("someId");
        notificationManager.notify(1, builder.build());

    }
}
