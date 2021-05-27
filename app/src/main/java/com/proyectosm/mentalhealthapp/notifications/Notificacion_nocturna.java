package com.proyectosm.mentalhealthapp.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.proyectosm.mentalhealthapp.MainActivity;
import com.proyectosm.mentalhealthapp.R;

public class Notificacion_nocturna extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Iniciliza la variable principal para las notificaciones
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 4, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Se crea la notificación para la noche
        Notification notification = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle("Buenas noches,")
                .setContentText("¿Te apetece explicarme cómo te ha ido el día de hoy?")
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        // Se lanza la notificación con el ID 0
        notificationManager.notify(0, notification);
    }
}
