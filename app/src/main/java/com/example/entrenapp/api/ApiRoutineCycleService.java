package com.example.entrenapp.api;

import androidx.lifecycle.LiveData;

import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineCycle;
import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRoutineCycleService {

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<RoutineCycle>>> getRoutineCycles(@Path("routineId") int routineId);

    //No hace falta
    @POST("routines/{routineId}/cycles")
    LiveData<ApiResponse<RoutineCycle>> addRoutineCycle(@Path("routineId") int routineId, @Body RoutineCycle cycle);

    @GET("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<RoutineCycle>> getRoutineCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    //No hace falta
    @PUT("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<RoutineCycle>> modifyRoutineCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId, @Body RoutineCycle cycle);

    //No hace falta
    @DELETE("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Void>> deleteRoutineCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

}
