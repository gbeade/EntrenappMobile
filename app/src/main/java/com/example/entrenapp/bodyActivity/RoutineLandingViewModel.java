package com.example.entrenapp.bodyActivity;

import android.app.Application;

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

import java.util.Date;

public class RoutineLandingViewModel extends AndroidViewModel {

    private MutableLiveData<Routine> OtherRoutines;
    private Integer UserId;
    private App app;

    public RoutineLandingViewModel(@NonNull Application application) {
        super(application);
        app = (App) getApplication();
        UserId = app.getPreferences().getUserId();
    }

    public LiveData<Routine> getOtherRoutines(){
        if(OtherRoutines == null){
            OtherRoutines = new MutableLiveData<>();
            loadMyRoutines();
        }

        return OtherRoutines;
    }


    private void loadMyRoutines(){

        app.getRoutineRepository().getRoutines().observeForever(new Observer<Resource<PagedList<RoutineAPI>>>() {
            @Override
            public void onChanged(Resource<PagedList<RoutineAPI>> pagedListResource) {
                if(pagedListResource.getData() != null && pagedListResource.getData().getContent().size() > 0){
                    for(RoutineAPI routine : pagedListResource.getData().getContent()){
                        if(! UserId.equals(routine.getUser().getId()) )
                            OtherRoutines.postValue(new Routine(routine.getName(),routine.getMetadata().getSport(), Routine.Difficulty.valueOf(routine.getDifficulty()),routine.getMetadata().getEquipacion(),new Date(routine.getDate()),routine.getScore(),routine.getMetadata().getDuracion()));
                    }
                }
            }
        });

    }

}
