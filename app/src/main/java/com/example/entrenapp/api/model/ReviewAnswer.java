package com.example.entrenapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewAnswer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("routine")
    @Expose
    private RoutineAPI routine;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public RoutineAPI getRoutine() {
        return routine;
    }

    public void setRoutine(RoutineAPI routine) {
        this.routine = routine;
    }

}
