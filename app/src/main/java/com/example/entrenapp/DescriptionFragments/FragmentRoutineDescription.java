package com.example.entrenapp.DescriptionFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineDescriptionBinding;

import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.recyclerView.CycleAdapter;


public class FragmentRoutineDescription extends Fragment {

    private Routine routine;
    FragmentRoutineDescriptionBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.routine = getArguments().getParcelable("Routine");

        fillRoutine(); //should be an API call.

        binding.routineTitle.setText(this.routine.getName());
        binding.duration.setText("Duracion: "+this.routine.getDuration()+" minutos");


        RecyclerView.Adapter adapter = new CycleAdapter(this.routine.getCycles(),getActivity());
        binding.routineDescriptionCyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        binding.routineDescriptionCyclerView.setAdapter(adapter);


        binding.btnTrain.setOnClickListener(v -> train());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoutineDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private void train(){ //llamado a navController
        Intent intent = new Intent(getActivity(), ExecuteRoutineActivity.class);
        intent.putExtra("Routine", this.routine);
        startActivity(intent);
    }

    private void fillRoutine(){
        Cycle c = new Cycle(0, "Ciclo de entrada en calor", "Para entrar en calor", "warmup", 0, 2, 0);
        c.addExercise(new Exercise(0, "Abdominales ligeros", "Tipo", 10));
        c.addExercise(new Exercise(1, "Respiros profundos", "Tipo", 20));
        c.addExercise(new Exercise(2, "Estiramiento de brazos", "Tipo", 13));
        Cycle c2 = new Cycle(1,"Enfriamiento","Para enfriar","cooldown",1,2,0);
        c2.addExercise(new Exercise(0, "Abdominales ligeros", "Tipo", 10));
        c2.addExercise(new Exercise(1, "Respiros profundos", "Tipo", 20));
        routine.addCycle(c);
        routine.addCycle(c2);

    }

}