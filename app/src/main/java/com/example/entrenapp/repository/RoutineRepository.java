package com.example.entrenapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.entrenapp.App;
import com.example.entrenapp.api.ApiResponse;
import com.example.entrenapp.api.ApiRoutineService;
import com.example.entrenapp.api.ApiClient;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.api.model.RoutineCycle;


public class RoutineRepository {

    private final ApiRoutineService apiService;


    public RoutineRepository(App application) {
        this.apiService = ApiClient.create(application,ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<RoutineAPI>>> getRoutines() {
        return new NetworkBoundResource<PagedList<RoutineAPI>, PagedList<RoutineAPI>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineAPI>>> createCall() {
                return apiService.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<RoutineAPI>>> getMyRoutines() {
        return new NetworkBoundResource<PagedList<RoutineAPI>, PagedList<RoutineAPI>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineAPI>>> createCall() {
                return apiService.getMyRoutines();
            }
        }.asLiveData();
    }


    public LiveData<Resource<PagedList<RoutineAPI>>> getMyFavouriteRoutines() {
        return new NetworkBoundResource<PagedList<RoutineAPI>, PagedList<RoutineAPI>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineAPI>>> createCall() {
                return apiService.getMyFavouriteRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineAPI>> modifyRoutineScore(RoutineAPI routine, int newScore){
        return new NetworkBoundResource<RoutineAPI, RoutineAPI>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineAPI>> createCall() {
                RoutineAPI auxRoutine = new RoutineAPI(routine.getId(), routine.getName(), routine.getDetail(), routine.getDate(), newScore, routine.getIsPublic(), routine.getDifficulty(), routine.getMetadata(), routine.getUser());
                return apiService.modifyRoutine(auxRoutine.getId(), auxRoutine);
            }
        }.asLiveData();
    }


}
