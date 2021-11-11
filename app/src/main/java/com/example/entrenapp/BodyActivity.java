package com.example.entrenapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.entrenapp.databinding.ActivityBodyBinding;
import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;

public class BodyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
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