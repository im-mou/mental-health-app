package com.proyectosm.mentalhealthapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.proyectosm.mentalhealthapp.databinding.ActivityMainBinding;
import com.proyectosm.mentalhealthapp.notifications.Notificacion_diurna_reciever;
import com.proyectosm.mentalhealthapp.notifications.Notificacion_nocturna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String CHANNEL_ID = "MH_App";

    //private FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    //private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/usuarios");

    DatabaseReference connectedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
//        FirebaseApp.initializeApp(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        CreateNotificationChannel(); // crear el canal de notificaciones
        setNotifications(22, 0, 0, 55);


        // cargar los datos del usuario -> almenos el userID para luego obtener el resto de datos


//        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
//
//        connectedRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                boolean connected = snapshot.getValue(Boolean.class);
//                if (connected) {
//                    Log.d("Firebase", "connected");
//                } else {
//                    Log.d("Firebase", "not connected");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("Firebase", "Listener was cancelled");
//            }
//        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");

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

        calendar_d.set(Calendar.HOUR_OF_DAY, h2);
        calendar_d.set(Calendar.MINUTE, m);
        calendar_d.set(Calendar.SECOND, s);

        calendar_d1.set(Calendar.HOUR_OF_DAY, h1);
        calendar_d1.set(Calendar.MINUTE, m);
        calendar_d1.set(Calendar.SECOND, s);

        calendar_n.set(Calendar.HOUR_OF_DAY, h);
        calendar_n.set(Calendar.MINUTE, m);
        calendar_n.set(Calendar.SECOND, s);

        Intent intent_d = new Intent(getApplicationContext(), Notificacion_diurna_reciever.class);
        Intent intent_d1 = new Intent(getApplicationContext(), Notificacion_diurna_reciever.class);
        Intent intent_n = new Intent(getApplicationContext(), Notificacion_nocturna.class);

        PendingIntent pendingIntent_d = PendingIntent.getBroadcast(getApplicationContext(), id, intent_d, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent_d1 = PendingIntent.getBroadcast(getApplicationContext(), id+1, intent_d1, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent_n = PendingIntent.getBroadcast(getApplicationContext(), id+2, intent_n, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar_d.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent_d);  //set repeating every 24 hours
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar_d1.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent_d1);  //set repeating every 24 hours
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar_n.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent_n);  //set repeating every 24 hours

    }

    public void CreateNotificationChannel() {

        CharSequence name = "MH App";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        mChannel.setDescription("Canal principal de la app");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(mChannel);
    }

}