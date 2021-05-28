package com.proyectosm.mentalhealthapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.proyectosm.mentalhealthapp.databinding.ActivityMainBinding;
import com.proyectosm.mentalhealthapp.notifications.Notificacion_diurna_reciever;
import com.proyectosm.mentalhealthapp.notifications.Notificacion_nocturna;
import com.proyectosm.mentalhealthapp.ui.initialconfig.InitialconfigActivity;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String CHANNEL_ID = "MH_App";

    DatabaseReference connectedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Se inicializa la barra de configuración y se le asignan los distintos fragments
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();

        // Se enlaza los objetos con la barra inferior
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Inicializa los parámetros para recibir notificaciones

        // en esta parte comprobamos si existe el token para identificar al usuairio,
        // En el caso de que no exista el token, redirigimos al usuairo a la paginas para el registro
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (token == "") {
            Intent intent = new Intent(MainActivity.this, InitialconfigActivity.class);
            startActivity(intent);
        }

        CreateNotificationChannel(); // crear el canal de notificaciones
        setNotifications(22, 0, 0, 55);

        // comprobar que las entradas de los journals ya existen en la base de datos
        gnerateJournalEntries(token);
    }

    private void gnerateJournalEntries(String token) {
        OkHttpClient client = new OkHttpClient();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/journal/generate")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNotifications(int h, int m, int s, int id) {
        Calendar calendar_d = Calendar.getInstance();
        Calendar calendar_d1 = Calendar.getInstance();
        Calendar calendar_n = Calendar.getInstance();

        int h1 = h-4, h2 = h-8;

        if (h <= 8) {
            h2 = 24-(8-h);
            if (h <= 4)
                h1 = 24-(4-h);
        }

        // Establece las horas que se recibirán las notificaciones
        calendar_d.set(Calendar.HOUR_OF_DAY, h2);
        calendar_d.set(Calendar.MINUTE, m);
        calendar_d.set(Calendar.SECOND, s);

        calendar_d1.set(Calendar.HOUR_OF_DAY, h1);
        calendar_d1.set(Calendar.MINUTE, m);
        calendar_d1.set(Calendar.SECOND, s);

        calendar_n.set(Calendar.HOUR_OF_DAY, h);
        calendar_n.set(Calendar.MINUTE, m);
        calendar_n.set(Calendar.SECOND, s);

        // Se lanzan los intentos
        Intent intent_d = new Intent(getApplicationContext(), Notificacion_diurna_reciever.class);
        Intent intent_d1 = new Intent(getApplicationContext(), Notificacion_diurna_reciever.class);
        Intent intent_n = new Intent(getApplicationContext(), Notificacion_nocturna.class);

        // Lanza las notificaciones a las horas establecidas
        PendingIntent pendingIntent_d = PendingIntent.getBroadcast(getApplicationContext(), id, intent_d, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent_d1 = PendingIntent.getBroadcast(getApplicationContext(), id+1, intent_d1, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent_n = PendingIntent.getBroadcast(getApplicationContext(), id+2, intent_n, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        // Realiza las notificaciones cada 24 horas
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar_d.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent_d);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar_d1.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent_d1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar_n.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent_n);

    }

    // Parámetros de la notificación
    public void CreateNotificationChannel() {
        CharSequence name = "MH App";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        mChannel.setDescription("Canal principal de la app");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(mChannel);
    }

}