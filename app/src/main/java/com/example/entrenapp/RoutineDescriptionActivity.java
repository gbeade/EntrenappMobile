package com.example.entrenapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.entrenapp.databinding.ActivityRoutineDescriptionBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;

public class RoutineDescriptionActivity extends AppCompatActivity {

    private Routine routine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.routine = getIntent().getParcelableExtra("Routine");
        ActivityRoutineDescriptionBinding binding = ActivityRoutineDescriptionBinding.inflate(getLayoutInflater());
        binding.routineTitle.setText(this.routine.getName());
        binding.duration.setText("Dutacion: "+this.routine.getDuration()+" minutos");

        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
}