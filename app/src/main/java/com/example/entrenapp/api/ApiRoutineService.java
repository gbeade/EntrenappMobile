package com.example.entrenapp.api;


import androidx.lifecycle.LiveData;


import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;


import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

import retrofit2.http.Path;

public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<PagedList<RoutineAPI>>> getRoutines();

    @GET("users/current/routines")
    LiveData<ApiResponse<PagedList<RoutineAPI>>> getMyRoutines();

    @GET("favourites")
    LiveData<ApiResponse<PagedList<RoutineAPI>>> getMyFavouriteRoutines();

    @POST("favourites/{routineId}")
    LiveData<ApiResponse<Void>> setFavourite(@Path("routineId") int routineId);

    @DELETE("favourites/{routineId}")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);

    @PUT("routine/{routineId}")
    LiveData<ApiResponse<RoutineAPI>> modifyRoutine(@Path("routineId") int routineId, @Body RoutineAPI routine);


}
