package com.example.entrenapp.executeRoutineActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityExecuteRoutineBinding;
import com.example.entrenapp.mainActivity.MainActivity;
import com.example.entrenapp.recyclerView.TimeTickCardAdapter;


import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;


        public class ExecuteRoutineActivity extends AppCompatActivity {

            private View root;
            ActivityExecuteRoutineBinding binding;
            RecyclerView rv;
            TimeTickCardAdapter adapter;

            private Routine routine;
            private Iterator<Cycle> cycleIterator;
            Cycle currentCycle;
            Exercise currentExercise;
            int currentCycleIdx = -1;
            int currentExerciseOfCycleIdx = 0;
            int currentRepetitionOfExercise = 0;

            boolean simplifiedExecution = false;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                this.routine = getIntent().getParcelableExtra("Routine");
                binding = ActivityExecuteRoutineBinding.inflate(getLayoutInflater());
                root = binding.getRoot();
                setContentView(root);

//               fillRoutine();

                binding.cancel.setOnClickListener(view -> onCancel());
                binding.playtoggle.setOnClickListener(view -> togglePlayPauseExercise());
                binding.rewind.setOnClickListener(view -> startIterations());


                TextView textView;
                textView = root.findViewById(R.id.routine_name);
                textView.setText(routine.getName());

                if (! simplifiedExecution) {
                    SnapHelper snapHelper = new LinearSnapHelper();
                    snapHelper.attachToRecyclerView(binding.cycleRecyclerView);
                }
                startIterations();
            }

            public void startIterations() {
                rv = binding.cycleRecyclerView;
                cycleIterator = routine.getCycles().iterator();
                Log.d("Start", Arrays.toString(routine.getCycles().toArray()));
                currentCycleIdx = -1;
                nextCycle();
            }

            public void nextCycle() {

                TextView textView;
                currentCycleIdx ++;
                // El ciclo a terminado, con todas sus repeticiones
                // Hay que pasar al proximo o terminar la rutina
                if (currentCycleIdx == 0 || currentCycleIdx >= currentCycle.getRepetitions()) {
                    if (cycleIterator.hasNext()) {
                        // Fetch cycle instance
                        currentCycle = cycleIterator.next(); // Setup header information for that cycle
                        textView = root.findViewById(R.id.cycle_name);
                        textView.setText(currentCycle.getName());
                        currentCycleIdx = 0;
                        // Setup adapter for the new collection of exercises
                        TimeTickCardAdapter adapter;
                        if (simplifiedExecution) {
                            binding.cycleRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                            adapter = new TimeTickCardAdapter(currentCycle.getExercises(), R.layout.exercise_summary_card, this);
                            adapter.setSimplified(true);
                        } else {
                            binding.cycleRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                            adapter = new TimeTickCardAdapter(currentCycle.getExercises(), R.layout.exec_exercise_card, this);
                        }
                        this.adapter = adapter;
                        binding.cycleRecyclerView.setAdapter(adapter);
                    } else {
                        //Se termino la rutina, cambiar por el intent a la pagina de favoritos
                        onCancel();
                        return;
                    }
                }

                textView = root.findViewById(R.id.cycle_remaining);
                textView.setText("Repeticiones de este ciclo completas: "+currentCycleIdx+" de "+currentCycle.getRepetitions());
                binding.cycleRecyclerView.smoothScrollToPosition(0);
                adapter.startCounterOnPosition(0); // TODO por que esta en null , hace que no funcione
            }

            public void nextExercise(int currentExercise) {
                currentExerciseOfCycleIdx = currentExercise;

                // TODO: exercise loop on repetition
//        if ( currentRepetitionOfExercise < currentCycle.getExercises().get(currentExercise).getRepetitions()) {
//            currentRepetitionOfExercise ++;
//            binding.cycleRecyclerView.smoothScrollToPosition(currentExerciseOfCycleIdx + (simplifiedExecution ? 1 : 0));
////            adapter.togglePlay(); adapter.togglePlay();
//            adapter.startCounterOnPosition(currentExerciseOfCycleIdx);
//            nextExercise(currentExercise);
//            return;
//        }

                currentRepetitionOfExercise = 0;
                if (currentExerciseOfCycleIdx == currentCycle.getExercises().size() - 1) {
                    Log.d("hola", "hola");
                    currentExerciseOfCycleIdx = 0;
                    nextCycle();
                } else {
                    //if (currentExercise > 0) adapter.stopCounterOnPosition(currentExercise);
                    ++currentExerciseOfCycleIdx;
                    binding.cycleRecyclerView.smoothScrollToPosition(currentExerciseOfCycleIdx + (simplifiedExecution ? 1 : 0));
                    adapter.startCounterOnPosition(currentExerciseOfCycleIdx);
                }
            }

//            private void fillRoutine(){
//                this.routine = new Routine(1, "Pecho","sd", Routine.Difficulty.expert, false, new Date(), 4, 25);
//                Cycle c = new Cycle(15, "Calentamiento", "Calentamiento", "warmup", 1, 2, -1);
//                c.addExercise(new Exercise(0, "Descanso", "Tipo", 3, 1));
//                routine.addCycle(c);
//
//                c = new Cycle(16, "Enfriamiento", "Enfriamiento", "cooldown", 2, 3, -1);
//                c.addExercise(new Exercise(0, "Descanso", "Tipo",2,1 ));
//                routine.addCycle(c);
//            }


            boolean init = false;
            public void togglePlayPauseExercise(){
                if ( simplifiedExecution && !init) {
                    adapter.startCounterOnPosition(0);
                    init = true;
                }
                adapter.togglePlay();
            }

            public void onCancel() {
                Intent intent = new Intent(this, MainActivity.class); // YourRoutinesActivity.class); //YourRoutinesActivity.class);
                startActivity(intent);
            }



        }