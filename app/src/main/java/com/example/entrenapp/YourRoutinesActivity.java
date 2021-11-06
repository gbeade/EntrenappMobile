package com.example.entrenapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;

import java.util.ArrayList;


public class YourRoutinesActivity extends AppCompatActivity {

    private ArrayList<Routine> routines= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityYourRoutinesBinding binding = ActivityYourRoutinesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fillRoutines();

        RecyclerView.Adapter adapter = new CardAdapter(this.routines, R.layout.extense_square_card, this);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.routineRecyclerView.setAdapter(adapter);

    }

    private void fillRoutines(){
        routines.add(new Routine("Pecho Plano yyy", "Pecho", Routine.Difficulty.XTREME, false, null, 4, 25));
        routines.add(new Routine("Pecho Plano xxx", "Brazos", Routine.Difficulty.XTREME, false, null, 4, 25));
    }


}
