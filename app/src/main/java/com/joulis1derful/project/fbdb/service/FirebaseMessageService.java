package com.joulis1derful.project.fbdb.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        Log.e("FirebaseMessageService", "onCreate");
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Log.i(TAG, "onMessageReceived: title : "+title);

        Intent intent = new Intent("MyData");
        intent.putExtra("title", title);
        intent.putExtra("body", body);

        //uncomment to fetch separate data like key or value of a note
     /*   for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d(TAG, "key, " + key + " value " + value);
        }*/

        broadcaster.sendBroadcast(intent);
    }
}
