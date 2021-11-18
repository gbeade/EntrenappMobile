package com.example.entrenapp.repository;

import com.example.entrenapp.apiClasses.Routine;

public class UserSession {

    private static Routine lastExecutedRoutine = null;

    public static void setLastExecutedRoutine(Routine r) {
        lastExecutedRoutine = r;
    }

    public static Routine getLastExecutedRoutine() {
        return lastExecutedRoutine;
    }


}
