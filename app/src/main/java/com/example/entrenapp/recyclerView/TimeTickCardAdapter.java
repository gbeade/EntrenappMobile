package com.example.entrenapp.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTickCardAdapter extends CardAdapter<Exercise> {

    ExecuteRoutineActivity act;
    Map<TimeTickViewHolder, TimeTickViewHolder> map = new HashMap<>();

    public TimeTickCardAdapter(List<Exercise> dataset, Integer layoutID, ExecuteRoutineActivity context) {
        super(dataset, layoutID, context);
        act = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutID, viewGroup, false);
        Timer timer = new Timer();
        TimeTickViewHolder ttvh = new TimeTickViewHolder(view);
        map.put(ttvh, ttvh);
        return ttvh;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        map.get(holder).stopTimer();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        map.get(holder).startTimer();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        map.get(viewHolder).setTimerStartedTo(dataset.get(position).getDuration());
    }

    public class TimeTickViewHolder extends ViewHolder {

        // Timer
        Timer timer;
        Integer time = 0;
        Integer duration = 0;

        boolean timerStarted = false;

        private class ModifyTimerTask extends TimerTask {

            void timerTick() {
                if (time > duration) {
                    bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "✓");
                    if ( time > duration + 3) {
                        act.nextExercise(getAdapterPosition());
                        cancel();
                    }
                } else {
                    bindTextViewWithData(r.getIdentifier("timer", "id", packageName), (duration-time)+"\'\'");
                }
                time++;
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

        public void setTimerStartedTo(int duration) {
            this.duration = duration;
        }

    }

}
