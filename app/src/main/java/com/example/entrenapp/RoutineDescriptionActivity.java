package com.example.entrenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RoutineDescriptionActivity extends AppCompatActivity {

    private Routine routine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);
//
//        Bundle parameters = getIntent().getExtras();
//        this.routine= (Routine) parameters.get("routine");


    }
}