package com.example.entrenapp.api;


import androidx.lifecycle.LiveData;


import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.Review;
import com.example.entrenapp.api.model.ReviewAnswer;
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

    @POST("reviews/{routineId}")
    LiveData<ApiResponse<Void>> addReview(@Path("routineId") int routineId, @Body Review review);


    @GET("users/current/reviews")
    LiveData<ApiResponse<PagedList<ReviewAnswer>>> getReviews();



    @GET("routines/{routineId}")
    LiveData<ApiResponse<RoutineAPI>> getRoutineById(@Path("routineId") int routineId);


}
