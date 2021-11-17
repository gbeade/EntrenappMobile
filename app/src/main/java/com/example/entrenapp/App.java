package com.example.entrenapp;

import android.app.Application;

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

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        sportRepository = new SportRepository(this);

        routineCycleRepository = new RoutineCycleRepository(this);

        routineRepository = new RoutineRepository(this);

        ContextSingleton.getInstance(getApplicationContext());


    }



}
