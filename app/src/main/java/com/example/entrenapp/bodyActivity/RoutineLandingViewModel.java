package com.example.entrenapp.bodyActivity;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.entrenapp.App;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;

import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.repository.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoutineLandingViewModel extends AndroidViewModel {

    private MutableLiveData<List<Routine>> OtherRoutines;
    private Integer UserId;
    private App app;

    public RoutineLandingViewModel(@NonNull Application application) {
        super(application);
        app = (App) getApplication();
        UserId = app.getPreferences().getUserId();
    }

    public LiveData<List<Routine>> getOtherRoutines(){
        if(OtherRoutines == null){
            OtherRoutines = new MutableLiveData<>();
            if(OtherRoutines.getValue() == null){
                OtherRoutines.setValue(new ArrayList<>());
            }
            loadMyRoutines();
        }

        return OtherRoutines;
    }


    private void loadMyRoutines(){

        app.getRoutineRepository().getRoutines().observeForever(pagedListResource -> {
            if(pagedListResource.getData() != null && pagedListResource.getData().getContent().size() > 0){
                for(RoutineAPI routine : pagedListResource.getData().getContent()){
                    if(! UserId.equals(routine.getUser().getId()) ){
                        List<Routine> routineList = new ArrayList<>(OtherRoutines.getValue());
                        Routine routine1 = new Routine(routine.getId(), routine.getName(),routine.getMetadata().getSport(), Routine.Difficulty.valueOf(routine.getDifficulty()),routine.getMetadata().getEquipacion(),new Date(routine.getDate()),routine.getScore(),routine.getMetadata().getDuracion(),routine.getMetadata());
                        routineList.add(routine1);
                        OtherRoutines.setValue(routineList);
                    }
                }
            }
        });

    }

    public void clear(){
        if(this.OtherRoutines!=null){
            this.OtherRoutines=null;
        }
    }

}
