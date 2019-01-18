package com.pagatodo.yaganaste.fcm;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.App;

import static com.pagatodo.yaganaste.commons.ConstantsKt.CODI_NOTIFICATIONS_ID;
import static com.pagatodo.yaganaste.commons.ConstantsKt.INTENT_TOKEN_FIREBASE;

/**
 * @author Juan Guerra on 10/04/2017.
 * @update Frank Manzo 13/12/2017
 */

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
    }
}