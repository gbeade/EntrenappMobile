package com.example.entrenapp.apiClasses;

import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.List;

public class Exercise implements Cardable {

    private int id;
    private String name;
    private String type;
    private int duration;
    private int repetitions = 1;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getRepetitions() { return repetitions;}


    public Exercise(int id, String name, String type, int duration) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.repetitions = 1;
    }

    public Exercise(int id, String name, String type, int duration, int repetitions) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.repetitions = repetitions;
    }

    @Override
    public List<CardCaption> getCaptions() {
        ArrayList<CardCaption> al = new ArrayList<>();
        al.add(new CardCaption("title", "Nombre", name));
        al.add(new CardCaption("subtitle1", "Duración", Integer.toString(duration)));
        al.add(new CardCaption("subtitle2", "Tipo", type));
        return al;
    }


}
