package com.example.entrenapp;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Observer;

import com.example.entrenapp.api.model.User;
import com.example.entrenapp.repository.Resource;
import com.example.entrenapp.repository.RoutineCycleRepository;
import com.example.entrenapp.repository.RoutineRepository;
import com.example.entrenapp.repository.SportRepository;
import com.example.entrenapp.repository.UserRepository;


public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private SportRepository sportRepository;
    private RoutineCycleRepository routineCycleRepository;
    private RoutineRepository routineRepository;

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    public RoutineCycleRepository getRoutineCycleRepository() { return routineCycleRepository; }

    public RoutineRepository getRoutineRepository(){return routineRepository;}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Application ","here");

        preferences = new AppPreferences(this);
        preferences.setUserId(2);
        preferences.setAuthToken(null);

        userRepository = new UserRepository(this);

        sportRepository = new SportRepository(this);

        routineCycleRepository = new RoutineCycleRepository(this);

        routineRepository = new RoutineRepository(this);

        userRepository.getCurrentUser().observeForever(userResource -> {
            if(userResource == null || userResource.getData()==null)
                return;
            preferences.setUserId(userResource.getData().getId());
        });


        ContextSingleton.getInstance(getApplicationContext());


    }



}
