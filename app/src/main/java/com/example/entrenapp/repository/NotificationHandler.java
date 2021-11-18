package com.example.entrenapp.repository;

import static android.provider.Settings.System.getString;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.entrenapp.ContextSingleton;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;

public class NotificationHandler {

    public static NotificationChannel nc;
    public static NotificationChannel getNotificationChannel(String channelId) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if ( nc == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ContextSingleton.getContext().getString(R.string.channel_name);
            String description = ContextSingleton.getContext().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            nc = new NotificationChannel(channelId, name, importance);
            nc.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ContextSingleton.getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(nc);
        }
        return nc;
    }
}
