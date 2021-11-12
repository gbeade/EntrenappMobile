package com.example.entrenapp.apiClasses;

import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.List;

public class Exercise implements Cardable {

    private int id;
    private String name;
    private String type;
    private int duration;

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



    public Exercise(int id, String name, String type, int duration) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
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
