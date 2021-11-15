package com.example.entrenapp.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTickCardAdapter extends CardAdapter<Exercise> {

    ExecuteRoutineActivity act;
    Map<TimeTickViewHolder, TimeTickViewHolder> map = new HashMap<>();
    TimeTickViewHolder currentVH;
    Map<Integer, TimeTickViewHolder> positionToVHMap = new HashMap<>();
    boolean superTimerStopped = false;
    boolean isSimplified = false;

    public void setSimplified( boolean s) {
        isSimplified = s;
    }

    public TimeTickCardAdapter(List<Exercise> dataset, Integer layoutID, ExecuteRoutineActivity context) {
        super(dataset, layoutID, context);
        act = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutID, viewGroup, false);
        TimeTickViewHolder ttvh = new TimeTickViewHolder(view);
        map.put(ttvh, ttvh);
        return ttvh;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (!isSimplified) map.get(holder).stopTimer();
        map.get(holder).bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "-");
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (!isSimplified) map.get(holder).startTimer(); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        currentVH = map.get(holder);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        map.get(viewHolder).setTimerStartedTo(dataset.get(position).getDuration());
        map.get(viewHolder).bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "-");
        positionToVHMap.put(position, map.get(viewHolder));
    }

    public class TimeTickViewHolder extends ViewHolder {

        // Timer
        Timer timer;
        Integer time = 0;
        Integer duration = 0;
        boolean timerStarted = false;
        int countdown = 3;

        private class ModifyTimerTask extends TimerTask {

            void timerTick() {
                if (timerStarted && !superTimerStopped) {
                    if ( countdown >= 0 ) {
                        bindTextViewWithData(r.getIdentifier("timer", "id", packageName), countdown+"!");
                        countdown --;
                        return;
                    } else if (time > duration) {
                        bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "✓");
                        cancel();
                        act.nextExercise(getAdapterPosition());
                    } else {
                        bindTextViewWithData(r.getIdentifier("timer", "id", packageName), (duration - time) + "\'\'");
                    }
                    time++;
                }
            }

            @Override
            public void run() {
                act.runOnUiThread(()->timerTick());
            }

        }

        public TimeTickViewHolder(View view) {
            super(view);
            bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "-");
            duration = 0;
        }

        public void stopTimer() {
            if (timerStarted) {
                timer.cancel();
                timerStarted = false;
            }
        }

        public void startTimer() {
            timerStarted = true;
            time = 0;
            timer = new Timer();
            timer.scheduleAtFixedRate(new ModifyTimerTask(), 500, 1000);
        }

        public void resetTimer() {
            stopTimer();
            startTimer();
        }

        public void playPauseTimer() {
            timerStarted = !timerStarted;
        }
        public void setTimerStartedTo(int duration) {
            this.duration = duration;
        }
    }

    public void pauseCurrentExercise(){
        superTimerStopped = true;
    }

    public void playCurrentExercise() {
        superTimerStopped = false;
    }

    public void togglePlay() {
        superTimerStopped = !superTimerStopped;
    }

    public void startCounterOnPosition(int position) {
        if( isSimplified && positionToVHMap.containsKey(position) )
            positionToVHMap.get(position).startTimer();
    }

    public void stopCounterOnPosition(int position) {
       if( isSimplified && positionToVHMap.containsKey(position) )
           positionToVHMap.get(position).stopTimer();
    }

    public boolean isPaused() {
        return !superTimerStopped;
    }


}
