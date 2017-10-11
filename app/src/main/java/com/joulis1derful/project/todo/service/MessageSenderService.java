package com.joulis1derful.project.todo.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageSenderService extends IntentService {

    private static final String TOKEN =
            "cxApZkjjpB0:APA91bEYmbuqowY0Xrh0XSutcQfDL97a6v_mmnmLDE6LVegEi-7Us6qgrD8CW8qTYNtPrZXxaByYvO20Y1fIV4aMO8hVYtoPBB2-i5mSddKWuqDBnC9Xg_PvIderjrJWqxauxz0zNMO-";

    public static final String LEGACY_SERVER_KEY =
            "AAAASKprTh8:APA91bG2Q6DhRf6vNLZ7FRQ6cJyAIu7OsPRau8BsO8pbSQk6WphOqmGU_MfRLLmUK15g50iu4S2mY96SmIOgCS9kTpBJHuAGLRDiUDlCfMON19IGSzlbztqWrAwFL8FQf478YxIJgQ-B";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = MessageSenderService.class.getSimpleName();

    public MessageSenderService() {
        super("serviceMessage");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //send message
        Log.e("IntentService", "handling");
        try {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.e(TAG, uid);
            OkHttpClient client = new OkHttpClient();
            JSONObject json=new JSONObject();
            JSONObject dataJson=new JSONObject();
            dataJson.put("body","Someone has created a note");
            dataJson.put("title","UPDATE");
            json.put("notification", dataJson);
            json.put("to",uid);
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder()
                    .header("Authorization","key="+ LEGACY_SERVER_KEY)
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(body)
                    .build();
//            Log.e("URL from builder:", request.toString().substring(0,20));

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("intentService", "failure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("intentService", "success");

                    Log.e("URL:", call.request().toString());
                }
            });

        } catch (Exception e) {
            Log.d("Exception", e + "");
            e.printStackTrace();
        }
    }
}
