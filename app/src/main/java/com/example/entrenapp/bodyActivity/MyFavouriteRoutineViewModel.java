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

public class MyFavouriteRoutineViewModel extends AndroidViewModel {

    private MutableLiveData<Routine> myFavouriteRoutines;

    public MyFavouriteRoutineViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Routine> getMyFavouriteRoutines(){
        if(myFavouriteRoutines == null){
            myFavouriteRoutines = new MutableLiveData<>();
            loadMyRoutines();
        }

        return myFavouriteRoutines;
    }


    private void loadMyRoutines(){
        App app = (App) getApplication();
        app.getRoutineRepository().getMyFavouriteRoutines().observeForever(new Observer<Resource<PagedList<RoutineAPI>>>() {
            @Override
            public void onChanged(Resource<PagedList<RoutineAPI>> pagedListResource) {
                if(pagedListResource.getData() != null && pagedListResource.getData().getContent().size() > 0){
                    for(RoutineAPI routine : pagedListResource.getData().getContent()){
                        myFavouriteRoutines.postValue(new Routine(routine.getId(), routine.getName(),routine.getMetadata().getSport(), Routine.Difficulty.valueOf(routine.getDifficulty()),routine.getMetadata().getEquipacion(),new Date(routine.getDate()),routine.getScore(),routine.getMetadata().getDuracion()));
                    }
                }
            }
        });

    }


}
