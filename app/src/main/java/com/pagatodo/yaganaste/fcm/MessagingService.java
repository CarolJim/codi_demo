package com.pagatodo.yaganaste.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.commons.Utils;
import com.pagatodo.yaganaste.dtos.Notification;
import com.pagatodo.yaganaste.modules.money_notification.MoneyNotification;
import com.pagatodo.yaganaste.modules.onboarding.Onboarding;

import androidx.core.app.NotificationCompat;

import static com.pagatodo.yaganaste.commons.ConstantsKt.CODI_NOTIFICATIONS_ID;
import static com.pagatodo.yaganaste.commons.ConstantsKt.INTENT_PUSH_NOTIFICATION;
import static com.pagatodo.yaganaste.commons.ConstantsKt.INTENT_TOKEN_FIREBASE;

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        App.getPreferences().saveData(CODI_NOTIFICATIONS_ID, token);
        App.getContext().sendBroadcast(new Intent(INTENT_TOKEN_FIREBASE));
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData() != null) {
            String json = remoteMessage.getData().get("data");
            Notification notification = new Gson().fromJson(json, Notification.class);
            if (Utils.Companion.isAppIsInBackground(getApplicationContext())) {
                sendNotification(notification);
            } else {
                showDialogNotification(notification);
            }
        } else if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, Onboarding.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "");
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setOnlyAlertOnce(true);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        notificationBuilder.setVibrate(pattern);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void sendNotification(Notification notification) {
        Intent intent = new Intent(this, Onboarding.class);
        intent.putExtra(INTENT_PUSH_NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "notify_001");
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(notification.getTitle());
        if (notification.getInfoCuenta() != null) {
            notificationBuilder.setContentText(Utils.Companion.processAccountValidation(notification.getInfoCuenta()));
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(Utils.Companion.processAccountValidation(notification.getInfoCuenta())));
        } else {
            notificationBuilder.setContentText(notification.getBody());
        }
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setOnlyAlertOnce(true);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        notificationBuilder.setVibrate(pattern);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001", "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void showDialogNotification(Notification notification) {
        Intent intent = new Intent(this, MoneyNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(INTENT_PUSH_NOTIFICATION, notification);
        startActivity(intent);
    }
}