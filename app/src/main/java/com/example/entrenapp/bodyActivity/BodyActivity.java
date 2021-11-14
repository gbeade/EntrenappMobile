package com.example.entrenapp.bodyActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;



import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.repository.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BodyActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        app = (App) getApplication();
        getUser();

    }

    private void getUser(){
        app.getUserRepository().getCurrentUser().observe(this, userResource -> {
            if(userResource.getStatus()== Status.SUCCESS){
                app.getPreferences().setUserId(userResource.getData().getId());
            }
        });

    }


}