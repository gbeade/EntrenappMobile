package com.example.entrenapp.repository;

import com.example.entrenapp.apiClasses.Routine;

public class UserSession {

    private static Routine lastExecutedRoutine = null;
    private static String username = null;
    private static Routine lastFavedRoutine = null;

    public static Routine getLastFavedRoutine() {
        return lastFavedRoutine;
    }

    public static void setLastFavedRoutine(Routine lastFavedRoutine) {
        UserSession.lastFavedRoutine = lastFavedRoutine;
    }

    public static boolean getSimpleExecution() {
        return simpleExecution;
    }

    public static void setSimpleExecution(boolean simpleExecution) {
        UserSession.simpleExecution = simpleExecution;
    }

    private static boolean simpleExecution = false;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }


    public static void setLastExecutedRoutine(Routine r) {
        lastExecutedRoutine = r;
    }

    public static Routine getLastExecutedRoutine() {
        return lastExecutedRoutine;
    }




}
