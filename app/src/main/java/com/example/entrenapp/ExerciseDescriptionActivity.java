package com.example.entrenapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.databinding.ActivityExerciseDescriptionBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;

public class ExerciseDescriptionActivity extends AppCompatActivity {

    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.exercise = getIntent().getParcelableExtra("Exercise");
        ActivityExerciseDescriptionBinding binding = ActivityExerciseDescriptionBinding.inflate(getLayoutInflater());
        binding.exerciseName.setText(exercise.getName());
        binding.exerciseDescription.setText(exercise.getType());

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