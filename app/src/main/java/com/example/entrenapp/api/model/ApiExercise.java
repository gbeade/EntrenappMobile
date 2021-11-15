package com.example.entrenapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiExercise {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("reps")
    @Expose
    private int reps;
    @SerializedName("equipacion")
    @Expose
    private boolean equipacion;

    /**
     * No args constructor for use in serialization
     *
     */
    public ApiExercise() {
    }

    /**
     *
     * @param name
     * @param time
     * @param id
     * @param reps
     * @param equipacion
     */
    public ApiExercise(String time, String name, int id, int reps, boolean equipacion) {
        super();
        this.time = time;
        this.name = name;
        this.id = id;
        this.reps = reps;
        this.equipacion = equipacion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReps() { return reps; }

    public void setReps(int reps) { this.reps = reps; }

    public boolean getEquipacion() { return equipacion; }

    public void setEquipacion(boolean equipacion) { this.equipacion = equipacion; }

}