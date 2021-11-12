package com.example.entrenapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BodyActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
//        ActivityBodyBinding binding = ActivityBodyBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

         bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment.getNavController());


//        ActivityBodyBinding binding = ActivityBodyBinding.inflate(getLayoutInflater());
//        View root = binding.getRoot();
//
//        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
//        setContentView(root);
//
//        binding2.toolbar.inflateMenu(R.menu.menu_main);
//        setSupportActionBar(binding2.toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
    }


}