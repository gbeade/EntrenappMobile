package com.example.entrenapp.apiClasses;

import java.util.ArrayList;
import java.util.List;

public class Cycle {

    private int id;
    private String name;
    private String detail;
    private String type;
    private int order;
    private int repetitions;
    private int metadata;

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public Cycle(int id, String name, String detail, String type, int order, int repetitions, int metadata) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
        this.metadata = metadata;
    }

    // Lista de ejercicios
    ArrayList<Exercise> exercises = new ArrayList<>();

    public void addExercise(Exercise e) {
        exercises.add(e);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
