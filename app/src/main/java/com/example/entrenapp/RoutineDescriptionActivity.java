package com.example.entrenapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityRoutineDescriptionBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.CycleAdapter;

public class RoutineDescriptionActivity extends AppCompatActivity {

    private Routine routine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.routine = getIntent().getParcelableExtra("Routine");

        fillRoutine(); //should be an API call.

        ActivityRoutineDescriptionBinding binding = ActivityRoutineDescriptionBinding.inflate(getLayoutInflater());
        binding.routineTitle.setText(this.routine.getName());
        binding.duration.setText("Duracion: "+this.routine.getDuration()+" minutos");

        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView.Adapter adapter = new CycleAdapter(this.routine.getCycles(),this);
        binding.routineDescriptionCyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.routineDescriptionCyclerView.setAdapter(adapter);


        binding.btnTrain.setOnClickListener(v -> train());
    }

    private void train(){
        Intent intent = new Intent(this, ExecuteRoutineActivity.class);
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