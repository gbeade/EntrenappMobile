package com.example.entrenapp.bodyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.entrenapp.App;
import com.example.entrenapp.DescriptionFragments.DescriptionActivity;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class FragmentRoutine extends Fragment implements CardAdapter.ViewHolder.OnNoteListener{
    protected ArrayList<Cardable> dataset = new ArrayList<>();
    protected Predicate<Cardable> filterFun = r -> true;
    protected App app ;
    protected CardAdapter.ViewHolder.OnNoteListener onNoteListener;
    protected boolean favourite = false;


    abstract public void fillRoutines();

    abstract public void updateRecyclerView();

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        intent.putExtra("Routine",  (Parcelable) this.dataset.get(position));
        intent.putExtra("Favourite",this.favourite);
        getActivity().getViewModelStore().clear();
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillRoutines();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        onNoteListener = this;
    }

    protected void responseViewModel(List<Routine> routine){
        if(routine.size() > 0  && dataset.size() == routine.size()-1) {
            dataset.add(routine.get(routine.size()-1));
        }else{
            if(routine.size() >= 0 ){
                dataset = new ArrayList<>();
                for(Routine routineItem : routine)
                    dataset.add(routineItem);
            }
        }

        updateRecyclerView();
    }


}
