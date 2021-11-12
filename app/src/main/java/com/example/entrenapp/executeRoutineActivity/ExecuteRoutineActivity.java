package com.example.entrenapp.executeRoutineActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityExecuteRoutineBinding;
import com.example.entrenapp.recyclerView.CardAdapter;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ExecuteRoutineActivity extends AppCompatActivity {

    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.routine = getIntent().getParcelableExtra("Routine");

        ActivityExecuteRoutineBinding binding = ActivityExecuteRoutineBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);


        fillRoutine();

        TextView textView;
        textView = (TextView) root.findViewById(R.id.routine_name);
        textView.setText(routine.getName());

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.cycleRecyclerView);
        for (Cycle cycle: routine.getCycles()) {
            textView = (TextView) root.findViewById(R.id.cycle_name);
            textView.setText(cycle.getName());
            textView = (TextView) root.findViewById(R.id.subtitle1);
            textView.setText(Integer.toString(cycle.getRepetitions()));
            for (int i=1; i<=cycle.getRepetitions(); i++) {
                textView = (TextView) root.findViewById(R.id.repetitionsRemaining);
                textView.setText(Integer.toString(i));

                // En cada iteración, recargamos el carrusel y volvemos al principio
                RecyclerView.Adapter adapter = new CardAdapter(cycle.getExercises(), R.layout.exec_exercise_card, this);
                binding.cycleRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.cycleRecyclerView.setAdapter(adapter);
                binding.cycleRecyclerView.scrollToPosition(0);


                // Correr todos los ejercicios. Cada un segundo modificar el cartel!
                int j =0;
                for (Exercise exercise: cycle.getExercises()) {
                    // Esperar la duracion del ejercicio y hacer el swipe a la derecha
                    binding.cycleRecyclerView.scrollToPosition(j);
                    j ++;
                }
            }
        }
    }

    private void fillRoutine(){
        Cycle c = new Cycle(0, "Ciclo de entrada en calor", "Para entrar en calor", "warmup", 0, 2, 0);
        c.addExercise(new Exercise(0, "Abdominales ligeros", "Tipo", 10));
        c.addExercise(new Exercise(1, "Respiros profundos", "Tipo", 20));
        c.addExercise(new Exercise(2, "Estiramiento de brazos", "Tipo", 13));
        routine.addCycle(c);
    }



}