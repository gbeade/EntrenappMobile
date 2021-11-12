package com.example.entrenapp.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entrenapp.bodyActivity.BodyActivity;
import com.example.entrenapp.databinding.ActivityMainBinding;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;


import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            login();
        });

    }

    public void login() {
        Intent intent = new Intent(this, BodyActivity.class); // YourRoutinesActivity.class); //YourRoutinesActivity.class);
        startActivity(intent);
    }



}