package com.example.entrenapp.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.entrenapp.R;
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
        map.get(viewHolder).setTimerStartedTo(dataset.get(position).getDuration());
        map.get(viewHolder).setRepetitionsOfTimer(dataset.get(position).getRepetitions());
        map.get(viewHolder).setExerciseName(dataset.get(position).getName());
        super.onBindViewHolder(viewHolder, position);
        map.get(viewHolder).bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "-");
        positionToVHMap.put(position, map.get(viewHolder));
    }

    public class TimeTickViewHolder extends ViewHolder {

        // Timer
        Timer timer;
        Integer time = 0;
        Integer duration = 0;
        int repetitions = 0;
        int currentRepetition = 0;
        String name;
        boolean timerStarted = false;
        int countdown = 3;
        private String[] rsg = { r.getString(R.string.rsg_ready), r.getString(R.string.rsg_set), r.getString(R.string.rsg_go)};

        private class ModifyTimerTask extends TimerTask {

            void timerTick() {
                if (timerStarted && !superTimerStopped) {
                    if (countdown == 3) bindName();
                    if ( countdown > 0 ) {
                        bindTextViewWithData(r.getIdentifier("timer", "id", packageName), rsg[3-countdown]);
                        countdown --;
                        return;
                    } else if ( duration == 0) {
                        bindTextViewWithData(r.getIdentifier("timer", "id", packageName), repetitions+" reps.");
                        cancel();
                        if ( isSimplified) {
                            act.togglePlayPauseExercise();
                            act.nextExercise(getAdapterPosition());
                        }
                        return;
                    } else if (time > duration) {
                           bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "✓");
                            if ( time > duration + 2) {
                                if (currentRepetition >= repetitions-1) {
                                    cancel();
                                    act.nextExercise(getAdapterPosition());
                                } else {
                                    bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "✓");
                                    currentRepetition ++;
                                    countdown = 3;
                                    time = 0;
                                    bindName();
                                    return;
                                }
                            }
                        } else {
                            bindTextViewWithData(r.getIdentifier("timer", "id", packageName), (duration - time ) + "\'\'");
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
            repetitions = 0;
            currentRepetition = 0;
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
            countdown = 3;
            currentRepetition = 0;
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
        public void setRepetitionsOfTimer(int repetitions) {
            this.repetitions = repetitions;
        }
        public void setExerciseName(String name) {this.name = name;}
        public String getExerciseName() {return this.name;}
        public void bindName() {
            if (repetitions == 1 || duration == 0) return;
            bindTextViewWithData(r.getIdentifier("title", "id", packageName), name+" ("+(currentRepetition+1)+"/"+repetitions+")");
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

    public void cleanTicks() {
        for (TimeTickViewHolder tv: map.keySet()) {
            tv.bindTextViewWithData(r.getIdentifier("timer", "id", packageName), "-");
            tv.bindTextViewWithData(r.getIdentifier("title", "id", packageName), tv.getExerciseName());
        }
    }

}
