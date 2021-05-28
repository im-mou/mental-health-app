package com.proyectosm.mentalhealthapp.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.widget.Toast;

import com.proyectosm.mentalhealthapp.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.proyectosm.mentalhealthapp.MainActivity.setNotifications;

public class Notificacion_diurna_action extends BroadcastReceiver {
    OkHttpClient client;

    @Override
    public void onReceive(Context context, Intent intent) {

        client = new OkHttpClient();

        double sentimentValue = intent.getDoubleExtra("emotion", 0.0);
        sentimentValue = sentimentValue * 0.2;

        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("sentiment_index", Double.toString(sentimentValue))
                .build();

        Request request = new Request.Builder()
                .url(context.getString(R.string.api_url)+"/journal/sentimentupdate")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, Double.toString(sentimentValue), Toast.LENGTH_SHORT).show();
    }
}
