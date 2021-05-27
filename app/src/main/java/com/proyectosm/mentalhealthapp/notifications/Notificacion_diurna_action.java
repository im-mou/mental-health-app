package com.proyectosm.mentalhealthapp.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Notificacion_diurna_action extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("emotion");

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
