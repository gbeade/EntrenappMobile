package com.example.entrenapp;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;

public abstract class FragmentRoutine extends Fragment implements CardAdapter.ViewHolder.OnNoteListener{
    protected ArrayList<Cardable> dataset = new ArrayList<>();
    protected Predicate<Cardable> filterFun = r -> true;



    protected void fillRoutines(){
        dataset.add(new Routine("Pecho Plano yyy", "Pecho", Routine.Difficulty.XTREME, false, new Date(), 4, 25));
        dataset.add(new Routine("Pecho Plano xxx", "Brazos", Routine.Difficulty.XTREME, true, new Date(), 3, 56));
        dataset.add(new Routine("Super atletico", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 5, 2));
        dataset.add(new Routine("Anti grasa 2.0", "Atletico", Routine.Difficulty.XTREME, true, new Date(), 8, 25));
        dataset.add(new Routine("Sprints 21", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 6, 40));
        dataset.add(new Routine("Picar", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 10, 25));
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), RoutineDescriptionActivity.class);
        intent.putExtra("Routine",  this.dataset.get(position));
        startActivity(intent);

    }
}
