package com.accubits.notifyexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static final String CHANNEL_ID = "sfdsffssafsa";
    NotificationChannel channel;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            String title =  remoteMessage.getNotification().getTitle();
            if(title!= null){
                Intent in = new Intent("intentKey");
                in.putExtra("key", title);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
            }

            Uri notificationSoundUri = Uri.parse("android.resource"+ "://" + BuildConfig.APPLICATION_ID + "/raw/insight.mp3");
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.insight);

            mNotificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                Log.e(TAG, notificationSoundUri.toString());

                if (notificationSoundUri != null) {
                    // Changing Default mode of notification
                    notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true);

//                    // Creating an Audio Attribute
//                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                            .setContentType(AudioAttributes.USAGE_NOTIFICATION)
//                            .setUsage(AudioAttributes.USAGE_ALARM)
//                            .build();
                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .build();

                    // Creating Channel
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "test", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setSound(sound, attributes);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.WHITE);
                    notificationChannel.enableVibration(true);
                    notificationBuilder.setChannelId(CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
            }
           /* mNotificationManager.notify(0, notificationBuilder.build());*/

        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
    }
}
