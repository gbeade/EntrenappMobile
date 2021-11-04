package com.example.entrenapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;

import java.util.ArrayList;


public class RoutineLandingActivity extends AppCompatActivity {

    private ArrayList<String> routines= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityYourRoutinesBinding binding = ActivityYourRoutinesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fillRoutines();

        RecyclerView.Adapter adapter = new RoutineAdapter(this.routines);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.routineRecyclerView.setAdapter(adapter);


    }

    private void fillRoutines(){
        for(int i = 0 ; i < 50 ; i++)
            routines.add("Pecho plano");
    }


}