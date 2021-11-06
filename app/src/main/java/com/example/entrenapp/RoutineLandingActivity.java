package com.example.entrenapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityRoutineLandingBinding;

import java.util.ArrayList;
import java.util.Date;


public class RoutineLandingActivity extends AppCompatActivity {

    private ArrayList<Cardable> dataset = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fillRoutines();

        ActivityRoutineLandingBinding binding = ActivityRoutineLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter1 = new CardAdapter(this.dataset, R.layout.extense_square_card, this);
        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);


        binding.myRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter2 = new CardAdapter(this.dataset, R.layout.summary_square_card, this);
        binding.myRoutinesRecyclerView.setAdapter(adapter2);

        binding.favouriteRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter3 = new CardAdapter(this.dataset, R.layout.summary_square_card, this);
        binding.favouriteRoutinesRecyclerView.setAdapter(adapter3);
    }

    private void fillRoutines(){
        for(int i = 0 ; i < 4 ; i++)
            dataset.add(new Routine("EjemploDeClase", "Brazos", Routine.Difficulty.XTREME, false, new Date(), 4, 10));
    }


}