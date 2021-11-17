package com.example.entrenapp.api;

import androidx.lifecycle.LiveData;


import com.example.entrenapp.api.model.Credentials;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.Token;
import com.example.entrenapp.api.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @GET("users")
    LiveData<ApiResponse<PagedList<User>>> getUsers();
}
