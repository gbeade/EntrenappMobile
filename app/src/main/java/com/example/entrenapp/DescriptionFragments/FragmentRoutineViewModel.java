package com.example.entrenapp.DescriptionFragments;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.entrenapp.App;
import com.example.entrenapp.api.model.ApiExercise;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineCycle;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.repository.Resource;
import com.example.entrenapp.repository.Status;

import java.util.ArrayList;
import java.util.List;

public class FragmentRoutineViewModel extends AndroidViewModel {

    private MutableLiveData<List<Cycle>> cycleList;
    private Routine routine;
    private Application app;

    public FragmentRoutineViewModel(@NonNull Application application) {
        super(application);
        app= application;
    }

    public LiveData<List<Cycle>> getCycle(){

        if(cycleList == null){
            cycleList = new MutableLiveData<>();
            if(cycleList.getValue()==null)
                cycleList.setValue(new ArrayList<>());
            loadCycle();
        }

        return cycleList;
    }

    public void setRoutineId(Routine routine) {
        this.routine=routine;
    }


    private void loadCycle(){
        App app = (App) getApplication();
        app.getRoutineCycleRepository().getRoutineCycles(routine.getId()).observeForever(new Observer<Resource<PagedList<RoutineCycle>>>() {
            @Override
            public void onChanged(Resource<PagedList<RoutineCycle>> pagedListResource) {
                if (pagedListResource.getStatus() == Status.SUCCESS) {
                    for (RoutineCycle cycle : pagedListResource.getData().getContent()) {
                        Cycle auxCycle = new Cycle(cycle.getId(), cycle.getName(), cycle.getDetail(), cycle.getType(), cycle.getOrder(), cycle.getRepetitions(), -1);
                        for (ApiExercise exercise : cycle.getMetadata().getEjercicios()) {
                            Exercise auxExercise = new Exercise(exercise.getId(), exercise.getName(), "Hola", Integer.valueOf(exercise.getTime()));
                            auxCycle.addExercise(auxExercise);
                        }
                        List<Cycle> list = new ArrayList<>(cycleList.getValue());
                        list.add(auxCycle);
                        cycleList.setValue(list);
                    }
                }
            }});

        }


}
