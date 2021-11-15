package com.example.entrenapp.executeRoutineActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityExecuteRoutineBinding;
import com.example.entrenapp.mainActivity.MainActivity;
import com.example.entrenapp.recyclerView.TimeTickCardAdapter;

import java.util.Date;
import java.util.Iterator;

// Todo: falla con un solo ejercicio
public class ExecuteRoutineActivity extends AppCompatActivity {

    private View root;
    ActivityExecuteRoutineBinding binding;
    RecyclerView rv;
    TimeTickCardAdapter adapter;

    private Routine routine= new Routine("Pecho Plano yyy", "Pecho", Routine.Difficulty.XTREME, false, new Date(), 4, 25);
    private Iterator<Cycle> cycleIterator;
    Cycle currentCycle;
    Exercise currentExercise;
    int currentCycleIdx = -1;
    int currentExerciseOfCycleIdx = 0;
    int currentRepetitionOfExercise = 0;

    boolean simplifiedExecution = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExecuteRoutineBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);

        fillRoutine();

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
        currentCycleIdx = -1;
        nextCycle();
    }

    public void nextCycle() {

        TextView textView;
        currentCycleIdx ++;


        if ( currentCycleIdx > 0 && currentCycle.getExercises().size() == 1) resetAdapter();

        // El ciclo ha terminado, con todas sus repeticiones
        // Hay que pasar al proximo o terminar la rutina
        if (currentCycleIdx == 0 || currentCycleIdx >= currentCycle.getRepetitions()) {
            if (cycleIterator.hasNext()) {
                // Fetch cycle instance
                currentCycle = cycleIterator.next(); // Setup header information for that cycle
                textView = root.findViewById(R.id.cycle_name);
                textView.setText(currentCycle.getName());
                currentCycleIdx = 0;
                // Setup adapter for the new collection of exercises
                resetAdapter();
            } else {
                //Se termino la rutina, cambiar por el intent a la pagina de favoritos
                if ( false /* TODO Chequear con API que la rutina sea del usuario*/) {
                    // Ejecuta Activity de puntuar

                } else {
                    showPopup();
                    // finish();
                }
                return;
            }
        }

        textView = root.findViewById(R.id.cycle_remaining);
        textView.setText("Repeticiones de este ciclo completas: "+currentCycleIdx+" de "+currentCycle.getRepetitions());
        binding.cycleRecyclerView.smoothScrollToPosition(0);
        //adapter.startCounterOnPosition(0); // TODO por que esta en null el hijo de puta, hace que no funcione
    }

    RatingBar rb;

    private void showPopup() {


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView;
        if (isRoutineRateable())
             popupView = inflater.inflate(R.layout.popup_finish_routine_rate, null);
        else
            popupView = inflater.inflate(R.layout.popup_finish_routine_norate, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        rb = findViewById(R.id.simpleRatingBar);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(findViewById(R.id.execRoutineContainer), Gravity.CENTER, 0, 0);
    }

    public void onClick(View view) {
        if ( isRoutineRateable() ) {
            Log.i("NUMSTARS", ""+rb.getNumStars());
            // rateRoutine();
        }
        finish();
    }

    private boolean isRoutineRateable() {
        return true;
    }

    private void resetAdapter() {
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
    }

    public void nextExercise(int currentExercise) {

        currentExerciseOfCycleIdx = currentExercise;

        Log.i("MESS", "Entering nextExercise for"+currentCycle.getExercises().get(currentExercise).getName()+"with value of currentRepetionsOnExercise="+currentRepetitionOfExercise);
        // TODO: exercise loop on repetition
        if ( currentRepetitionOfExercise < currentCycle.getExercises().get(currentExercise).getRepetitions()) {
            Log.i("EXREP", "Entering repetition of"+currentCycle.getExercises().get(currentExercise).getName());
            currentRepetitionOfExercise ++;
//            binding.cycleRecyclerView.smoothScrollToPosition(currentExerciseOfCycleIdx+1);
//            binding.cycleRecyclerView.smoothScrollToPosition(currentExerciseOfCycleIdx);
            nextExercise(currentExercise);
            currentRepetitionOfExercise = 0;
            return;
        }

        if (currentExerciseOfCycleIdx == currentCycle.getExercises().size() - 1) {
            currentExerciseOfCycleIdx = 0;
            init = false;
            nextCycle();
        } else {
            //if (currentExercise > 0) adapter.stopCounterOnPosition(currentExercise);
            ++currentExerciseOfCycleIdx;
            binding.cycleRecyclerView.smoothScrollToPosition(currentExerciseOfCycleIdx + (simplifiedExecution ? 1 : 0));
            adapter.startCounterOnPosition(currentExerciseOfCycleIdx);
        }
    }

    private void fillRoutine(){
        Cycle c = new Cycle(0, "Ciclo de entrada en calor", "Para entrar en calor", "warmup", 0, 2, 0);
        c.addExercise(new Exercise(0, "Abdominales ligeros", "Tipo", 1, 3));
//        c.addExercise(new Exercise(1, "Respiros profundos", "Tipo", 1));
        routine.addCycle(c);

        //c = new Cycle(0, "Ciclo atletico intenso", "Para entrenar fuerte", "warmup", 0, 1, 0);
        //c.addExercise(new Exercise(0, "Pique veloz", "Tipo", 3));
//        c.addExercise(new Exercise(1, "Salto en soga", "Tipo", 2));
//        c.addExercise(new Exercise(2, "Burpees", "Tipo", 2));
        //routine.addCycle(c);
    }


    boolean init = false;
    public void togglePlayPauseExercise(){
        if ( simplifiedExecution && !init) {
            adapter.startCounterOnPosition(0);
            init = true;
            return;
        }
        adapter.togglePlay();
    }

    public void onCancel() {
        Intent intent = new Intent(this, MainActivity.class); // YourRoutinesActivity.class); //YourRoutinesActivity.class);
        startActivity(intent);
    }



}