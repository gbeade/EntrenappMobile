package com.example.entrenapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.entrenapp.App;
import com.example.entrenapp.api.ApiClient;
import com.example.entrenapp.api.ApiResponse;
import com.example.entrenapp.api.ApiRoutineCycleService;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineCycle;

import java.util.ArrayList;

public class RoutineCycleRepository {

    private final ApiRoutineCycleService apiService;

    public RoutineCycleRepository(App application){
        this.apiService = ApiClient.create(application, ApiRoutineCycleService.class);
    }

    public LiveData<Resource<PagedList<RoutineCycle>>> getRoutineCycles(int routineId){
        return new NetworkBoundResource<PagedList<RoutineCycle>, PagedList<RoutineCycle>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineCycle>>> createCall() {
                return apiService.getRoutineCycles(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineCycle>> addRoutineCycle(int routineId, RoutineCycle cycle){
        return new NetworkBoundResource<RoutineCycle, RoutineCycle>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineCycle>> createCall() {
                return apiService.addRoutineCycle(routineId, cycle);
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineCycle>> getRoutineCycle(int routineId, int cycleId){
        return new NetworkBoundResource<RoutineCycle, RoutineCycle>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineCycle>> createCall() {
                return apiService.getRoutineCycle(routineId, cycleId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineCycle>> modifyRoutineCycle(int routineId, RoutineCycle cycle){
        return new NetworkBoundResource<RoutineCycle, RoutineCycle>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineCycle>> createCall() {
                return apiService.modifyRoutineCycle(routineId, cycle.getId(), cycle);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteRoutineCycle(int routineId, int cycleId){
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteRoutineCycle(routineId, cycleId);
            }
        }.asLiveData();
    }
}
