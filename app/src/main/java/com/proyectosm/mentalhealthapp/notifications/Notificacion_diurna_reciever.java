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

public class Notificacion_diurna_reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Iniciliza la variable principal para las notificaciones
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Botón de respuesta 1 sentiment = mal
        Intent broadcastIntent = new Intent(context, Notificacion_diurna_action.class);
        broadcastIntent.putExtra("emotion", "mal");
        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 1, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Botón de respuesta 2 sentiment = normal
        Intent broadcastIntent1 = new Intent(context, Notificacion_diurna_action.class);
        broadcastIntent1.putExtra("emotion", "normal");
        PendingIntent actionIntent1 = PendingIntent.getBroadcast(context, 2, broadcastIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        // Botón de respuesta 3 sentiment = bien
        Intent broadcastIntent2 = new Intent(context, Notificacion_diurna_action.class);
        broadcastIntent2.putExtra("emotion", "bien");
        PendingIntent actionIntent2 = PendingIntent.getBroadcast(context, 3, broadcastIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        // Se crea la notificación con sus variables importantes (titulo, cuerpo, botones...)
        Notification notification = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle("Hola,")
                .setContentText("¿Cómo te sientes ahora mismo?")
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Enfadado / Triste", actionIntent)
                .addAction(R.mipmap.ic_launcher, "Indifrente", actionIntent1)
                .addAction(R.mipmap.ic_launcher, "Feliz / Contento", actionIntent2)
                .build();

        // Se lanza con el ID 0
        notificationManager.notify(0, notification);
    }
}
