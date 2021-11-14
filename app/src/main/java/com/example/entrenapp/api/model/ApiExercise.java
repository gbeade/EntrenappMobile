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
     */
    public ApiExercise(String time, String name, int id) {
        super();
        this.time = time;
        this.name = name;
        this.id = id;
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

}