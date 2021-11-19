package com.example.entrenapp.repository;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.bodyActivity.BodyActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReminderBroadcast extends BroadcastReceiver {
    private static int id = 200;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHandler.getNotificationChannel(context.getString(R.string.app_name));

        Intent myIntent = new Intent(context, BodyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                0,
                myIntent,
                0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                .setContentTitle(context.getString(R.string.time_to_train))
                .setContentText(context.getString(R.string.routine_waiting_for_you)+(intent.getExtras().get("routineName") == null ? "" : " "+intent.getExtras().get("routineName")))
                .setSmallIcon(R.drawable.ic_dumbell)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(id++, builder.build());
    }

    public ReminderBroadcast() {
        super();
    }
}
