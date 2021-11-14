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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFavouriteRoutineViewModel extends AndroidViewModel {

    private MutableLiveData<List<Routine>> myFavouriteRoutines;

    public MyFavouriteRoutineViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Routine>> getMyFavouriteRoutines(){
        if(myFavouriteRoutines == null){
            myFavouriteRoutines = new MutableLiveData<>();
            if(myFavouriteRoutines.getValue()==null)
                myFavouriteRoutines.setValue(new ArrayList<>());
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
                        List<Routine> routineList = new ArrayList<>(myFavouriteRoutines.getValue());
                        Routine routine1 = new Routine(routine.getId(), routine.getName(),routine.getMetadata().getSport(), Routine.Difficulty.valueOf(routine.getDifficulty()),routine.getMetadata().getEquipacion(),new Date(routine.getDate()),routine.getScore(),routine.getMetadata().getDuracion());
                        routineList.add(routine1);
                        myFavouriteRoutines.setValue(routineList);                    }
                }
            }
        });
    }


}
