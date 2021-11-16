package com.example.entrenapp.DescriptionFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.databinding.ActivityExerciseDescriptionBinding;
import com.example.entrenapp.databinding.FragmentExerciseDescriptionBinding;


public class FragmentExerciseDescription extends Fragment {


    private Exercise exercise;
    private FragmentExerciseDescriptionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.exercise = FragmentExerciseDescriptionArgs.fromBundle(getArguments()).getExercise();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.exerciseName.setText(this.exercise.getName());
        binding.exerciseDescription.setText(this.exercise.getType());
        binding.exerciseReps.setText(this.exercise.getRepetitions());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExerciseDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}