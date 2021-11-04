package com.example.entrenapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;


public class YourRoutinesActivity extends AppCompatActivity {

    private String[] routines= new String[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("on second activity","nO SE PUDO");

        super.onCreate(savedInstanceState);

        ActivityYourRoutinesBinding binding = ActivityYourRoutinesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        for(int i = 0 ; i < 10 ; i++)
            addItem(i);

        RecyclerView.Adapter adapter = new RoutineAdapter(this.routines);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.routineRecyclerView.setAdapter(adapter);





    }


    private void addItem(int index){
        routines[index] = "Pecho Plano";
    }


}
