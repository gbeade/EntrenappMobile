package com.example.entrenapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityRoutineLandingBinding;
import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;

import java.util.ArrayList;


public class RoutineLandingActivity extends AppCompatActivity {

    private ArrayList<Cardable> dataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fillRoutines();


        // binding.getRoute.class == Layout
        ActivityRoutineLandingBinding binding = ActivityRoutineLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Creamos un nuevo adaptador y se lo asociamos al recycler
        // El adaptador recibe una colección de datos, y es el encargado de ir liberando de administrar tanta información
        RecyclerView.Adapter adapter = new CardAdapter(this.dataset, R.layout.extense_routine_description, this);
        binding.routineRecyclerView.setAdapter(adapter);
    }

    private void fillRoutines(){
        for(int i = 0 ; i < 2 ; i++)
            dataset.add(new Routine("EjemploDeClase", "Brazos", Routine.Difficulty.XTREME, false, null, 4, 10));
    }


}