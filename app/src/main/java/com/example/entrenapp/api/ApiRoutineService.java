package com.example.entrenapp.api;


import androidx.lifecycle.LiveData;


import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;

import retrofit2.http.GET;

public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<PagedList<RoutineAPI>>> getRoutines();

    @GET("users/current/routines")
    LiveData<ApiResponse<PagedList<RoutineAPI>>> getMyRoutines();

    @GET("favourites")
    LiveData<ApiResponse<PagedList<RoutineAPI>>> getMyFavouriteRoutines();

}
