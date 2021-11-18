package com.example.entrenapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.entrenapp.App;
import com.example.entrenapp.api.ApiResponse;
import com.example.entrenapp.api.ApiRoutineService;
import com.example.entrenapp.api.ApiClient;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.Review;
import com.example.entrenapp.api.model.ReviewAnswer;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.api.model.RoutineCycle;


public class RoutineRepository {

    private final ApiRoutineService apiService;


    public RoutineRepository(App application) {
        this.apiService = ApiClient.create(application, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<RoutineAPI>>> getRoutines() {
        return new NetworkBoundResource<PagedList<RoutineAPI>, PagedList<RoutineAPI>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineAPI>>> createCall() {
                return apiService.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<RoutineAPI>>> getMyRoutines() {
        return new NetworkBoundResource<PagedList<RoutineAPI>, PagedList<RoutineAPI>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineAPI>>> createCall() {
                return apiService.getMyRoutines();
            }
        }.asLiveData();
    }


    public LiveData<Resource<PagedList<RoutineAPI>>> getMyFavouriteRoutines() {
        return new NetworkBoundResource<PagedList<RoutineAPI>, PagedList<RoutineAPI>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineAPI>>> createCall() {
                return apiService.getMyFavouriteRoutines();
            }
        }.asLiveData();
    }


    public LiveData<Resource<Void>> setFavourite(Integer routineId) {
        return new NetworkBoundResource<Void, Void>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.setFavourite(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteFavourite(Integer routineId) {
        return new NetworkBoundResource<Void, Void>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> addReview(RoutineAPI routine, int newScore) {
        return new NetworkBoundResource<Void, Void>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addReview(routine.getId(), new Review(newScore,""));
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineAPI>> getRoutineById(Integer routineId) {
        return new NetworkBoundResource<RoutineAPI, RoutineAPI>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineAPI>> createCall() {
                return apiService.getRoutineById(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<ReviewAnswer>>> getReviews() {
        return new NetworkBoundResource<PagedList<ReviewAnswer>, PagedList<ReviewAnswer>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<ReviewAnswer>>> createCall() {
                return apiService.getReviews();
            }
        }.asLiveData();
    }





}
