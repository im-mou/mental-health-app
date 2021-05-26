package com.proyectosm.mentalhealthapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.proyectosm.mentalhealthapp.databinding.ActivityMainBinding;

import com.proyectosm.mentalhealthapp.databinding.FragmentDashboardBinding;
import com.proyectosm.mentalhealthapp.ui.dashboard.DashboardFragment;
import com.proyectosm.mentalhealthapp.ui.dashboard.NewDashboard;
import com.proyectosm.mentalhealthapp.ui.home.HomeFragment;
import com.proyectosm.mentalhealthapp.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){

                if(menuItem.getItemId() == R.id.navigation_home)
                {
                    showSelectedFragment(new HomeFragment());
                }

                if(menuItem.getItemId() == R.id.navigation_dashboard)
                {
                    showSelectedFragment(new NewDashboard());
                }

                if(menuItem.getItemId() == R.id.navigation_notifications)
                {
                    showSelectedFragment(new NotificationsFragment());
                }
                return true;
            }
        });
        
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);+*/
    }

    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                commit();
    }

}