/*
* NAME: Tora Mullings
* SB ID: 111407756
* */
package com.example.storytime;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.app.Application;


/**
 * The App class is responsible for creating the notification channel.
 * The template for the class is from this tutorial site,
 * https://codinginflow.com/tutorials/android/notifications-notification-channels/part-1-notification-channels
 *
 */
public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    /**
     * Due to the changes in SDK 26 (Oreo), we must use slightly different code for making
     * a notification channel. We check the SDK, then instantiate the channel.
     * I made the importance "high" so that the notification can be seen and heard in-app.
     */
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
