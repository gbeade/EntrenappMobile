package com.example.entrenapp.executeRoutineActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.api.model.User;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.bodyActivity.BodyActivity;
import com.example.entrenapp.databinding.ActivityExecuteRoutineBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;
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

        binding.play.setOnClickListener(view -> togglePlayPauseExercise());
        binding.pause.setOnClickListener(view -> togglePlayPauseExercise());
        binding.rewind.setOnClickListener(view -> startIterations());

        app = (App) getApplication();

        app.getRoutineRepository().getRoutineById(routine.getId()).observe(activity, routineAPIResource -> {
            if(routineAPIResource == null || routineAPIResource.getData() == null)
                return;
            routineAPI = routineAPIResource.getData();
            isRoutineRateable = routineAPI.getUser().getId().equals(app.getPreferences().getUserId());
            });



        Toolbar tb = ((Toolbar)binding.getRoot().findViewById(R.id.toolbar).findViewById(R.id.toolbar));
        tb.setTitle(routine.getName());
        binding.getRoot().findViewById(R.id.toolbar).findViewById(R.id.imageView).setVisibility(View.GONE);

        TextView textView;
        rv = binding.cycleRecyclerView;

        if (! simplifiedExecution) {
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(rv);
        }

//        rv.setVerticalScrollBarEnabled(simplifiedExecution);
        rv.setHorizontalScrollBarEnabled(!simplifiedExecution);


        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        startIterations();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 16908332) {
          finish();
          return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    public void startIterations() {
        cycleIterator = routine.getCycles().iterator();
        currentCycleIdx = -1;
        nextCycle();
    }

    public void nextCycle() {

        TextView textView;
        currentCycleIdx ++;

        if (currentCycleIdx > 0 )
            Toast.makeText(this, getString(R.string.cycle_completed), Toast.LENGTH_SHORT).show();


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
        textView.setText(getString(R.string.current_exec_cycle)+": "+currentCycleIdx+" / "+currentCycle.getRepetitions());
        adapter.cleanTicks();
        binding.cycleRecyclerView.smoothScrollToPosition(0);
        //adapter.startCounterOnPosition(0);
    }

    RatingBar rb;
    Button popupButton;
    private void showPopup() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView;
        if (isRoutineRateable())
            popupView = inflater.inflate(R.layout.popup_finish_routine_rate, null);
        else
            popupView = inflater.inflate(R.layout.popup_finish_routine_norate, null);

        popupButton = popupView.findViewById(R.id.btn_return);
        popupButton.setOnClickListener(view->onClick());

        // create the popup window
        int width = (int)getResources().getDisplayMetrics().density * 450;
        int height = (int)getResources().getDisplayMetrics().density * 300;
        popupWindow = new PopupWindow(popupView, width, height, true);

        rb = popupView.findViewById(R.id.simpleRatingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if ( rating > 0) {
                    popupButton.setText(getString(R.string.leave_popup_with_rating));
                } else {
                    popupButton.setText(getString(R.string.leave_popup_without_rating));
                }
            }
        });
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(findViewById(R.id.execRoutineContainer), Gravity.CENTER, 0, 0);
    }


    public void onClick() {
        if ( isRoutineRateable() ) {
            app.getRoutineRepository().modifyRoutineScore(routineAPI,(int)(rb.getRating()*2)).observe(this, routineAPIResource -> {
                if(routineAPIResource == null || routineAPIResource.getData() == null)
                    return;
                popupWindow.dismiss();
                finish();
            });
        }
        else{
            popupWindow.dismiss();
            finish();
        }

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
        if (!simplifiedExecution) {
            adapter.togglePlay();
        }
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
            ((TextView) findViewById(R.id.play)).setVisibility(View.INVISIBLE);
            return;
        }
        swapPlayPauseIcon();
        adapter.togglePlay();
    }

    private void swapPlayPauseIcon() {
        if ( adapter.isPaused() ) {
            ((TextView) findViewById(R.id.play)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.pause)).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) findViewById(R.id.pause)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.play)).setVisibility(View.INVISIBLE);}
    }

}