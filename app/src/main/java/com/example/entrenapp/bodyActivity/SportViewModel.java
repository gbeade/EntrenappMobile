package com.example.entrenapp.bodyActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.entrenapp.App;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.Sport;
import com.example.entrenapp.repository.Resource;

import java.util.ArrayList;
import java.util.List;

public class SportViewModel extends AndroidViewModel {

    private MutableLiveData<List<Sport>> sportLiveData = new MutableLiveData<>();
    private App app;

    public SportViewModel(@NonNull Application application) {
        super(application);
        app=(App)getApplication();
    }

    public LiveData<List<Sport>> getSports(){
        if(sportLiveData.getValue() == null){
            sportLiveData.setValue(new ArrayList<>());
            loadFromApi();
        }
        return sportLiveData;
    }

    private void loadFromApi(){
        app.getSportRepository().getSports().observeForever(pagedListResource -> {
            if(pagedListResource.getData() == null || pagedListResource.getData().getContent().size()==0)
                return;
            for(Sport sport : pagedListResource.getData().getContent()){
                List<Sport> sportList = new ArrayList<>(sportLiveData.getValue());
                sportList.add(sport);
                sportLiveData.setValue(sportList);
            }
        });


    }




}
