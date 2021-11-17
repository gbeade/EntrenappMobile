package com.example.entrenapp.executeRoutineActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
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

import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.api.model.User;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityExecuteRoutineBinding;
import com.example.entrenapp.mainActivity.MainActivity;
import com.example.entrenapp.recyclerView.TimeTickCardAdapter;
import com.example.entrenapp.repository.Resource;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class ExecuteRoutineActivity extends AppCompatActivity {

    private View root;
    ActivityExecuteRoutineBinding binding;
    RecyclerView rv;
    TimeTickCardAdapter adapter;
    private Routine routine;
    //new Routine(1, "Pecho","Pecho", Routine.Difficulty.rookie, false, new Date(), 4, 25, null);
    private Iterator<Cycle> cycleIterator;
    Cycle currentCycle;
    Exercise currentExercise;
    int currentCycleIdx = -1;
    int currentExerciseOfCycleIdx = 0;
    int currentRepetitionOfExercise = 0;
    PopupWindow popupWindow;
    boolean isRoutineRateable;
    App app;
    boolean simplifiedExecution = true;
    LifecycleOwner activity = this;
    RoutineAPI routineAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExecuteRoutineBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);
        this.routine= getIntent().getParcelableExtra("Routine");

        binding.cancel.setOnClickListener(view -> onCancel());
        binding.play.setOnClickListener(view -> togglePlayPauseExercise());
        binding.pause.setOnClickListener(view -> togglePlayPauseExercise());
        binding.rewind.setOnClickListener(view -> startIterations());

        app = (App) getApplication();
        app.getUserRepository().getCurrentUser().observe(this, userResource -> {
            if(userResource == null || userResource.getData() == null)
                return;
            app.getRoutineRepository().getRoutineById(routine.getId()).observe(activity, routineAPIResource -> {
                if(routineAPIResource == null || routineAPIResource.getData() == null)
                    return;
                routineAPI = routineAPIResource.getData();
                isRoutineRateable = routineAPIResource.getData().getUser().getId().equals(userResource.getData().getId());
            });
        });


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

        if ( currentCycleIdx > 0 && !simplifiedExecution ) togglePlayPauseExercise();

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
                adapter.pauseCurrentExercise();
                showPopup();

                return;
            }
        }

        textView = root.findViewById(R.id.cycle_remaining);
        textView.setText("Repeticiones de este ciclo completas: "+currentCycleIdx+" de "+currentCycle.getRepetitions());
        adapter.cleanTicks();
        binding.cycleRecyclerView.smoothScrollToPosition(0);
        //adapter.startCounterOnPosition(0);
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

        Button btn = popupView.findViewById(R.id.btn_return);
        btn.setOnClickListener(view->onClick());

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
         popupWindow = new PopupWindow(popupView, width, height, true);

        rb = popupView.findViewById(R.id.simpleRatingBar);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(findViewById(R.id.execRoutineContainer), Gravity.CENTER, 0, 0);
    }

    public void onClick() {
        if ( isRoutineRateable() ) {
            app.getRoutineRepository().modifyRoutineScore(routineAPI,(int)(rb.getRating()*2));
        }
        popupWindow.dismiss();
        finish();
    }

    private boolean isRoutineRateable() {
        return isRoutineRateable;
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
        if (currentExerciseOfCycleIdx == currentCycle.getExercises().size() - 1) {
            currentExerciseOfCycleIdx = 0;
            init = false;
            if ( simplifiedExecution) swapPlayPauseIcon();
            nextCycle();
        } else {
            //if (currentExercise > 0) adapter.stopCounterOnPosition(currentExercise);
            ++currentExerciseOfCycleIdx;
            binding.cycleRecyclerView.smoothScrollToPosition(currentExerciseOfCycleIdx + (simplifiedExecution ? 1 : 0));
            adapter.startCounterOnPosition(currentExerciseOfCycleIdx);
        }
    }


    boolean init = false;
    public void togglePlayPauseExercise(){
        if ( simplifiedExecution && !init) {
            adapter.startCounterOnPosition(0);
            init = true;
            ((TextView) findViewById(R.id.pause)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.play)).setVisibility(View.GONE);
            return;
        }
        swapPlayPauseIcon();
        adapter.togglePlay();
    }

    private void swapPlayPauseIcon() {
        if ( adapter.isPaused() ) {
            ((TextView) findViewById(R.id.play)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.pause)).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.pause)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.play)).setVisibility(View.GONE);}
    }

    public void onCancel() {
        Intent intent = new Intent(this, MainActivity.class); // YourRoutinesActivity.class); //YourRoutinesActivity.class);
        startActivity(intent);
    }



}