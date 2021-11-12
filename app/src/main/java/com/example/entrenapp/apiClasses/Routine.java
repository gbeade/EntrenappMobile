package com.example.entrenapp.apiClasses;

import android.os.Parcel;
import android.os.Parcelable;


import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Routine implements Cardable,Parcelable {

    protected Routine(Parcel in) {
        name = in.readString();
        category = in.readString();
        byte tmpIsEquipmentRequired = in.readByte();
        isEquipmentRequired = tmpIsEquipmentRequired == 0 ? null : tmpIsEquipmentRequired == 1;
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            punctuation = null;
        } else {
            punctuation = in.readInt();
        }
    }

    public static final Creator<Routine> CREATOR = new Creator<Routine>() {
        @Override
        public Routine createFromParcel(Parcel in) {
            return new Routine(in);
        }

        @Override
        public Routine[] newArray(int size) {
            return new Routine[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeByte((byte) (isEquipmentRequired == null ? 0 : isEquipmentRequired ? 1 : 2));
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        if (punctuation == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(punctuation);
        }
    }

    public enum Difficulty {
        BEGINNER("Principiante"),
        EASY("Fácil"),
        MEDIUM("Mediana"),
        HARD("Difícil"),
        XTREME("Extrema");

        String label;

        Difficulty(String label) {
            this.label = label;
        }
    }

    private String name;
    private String category;
    private Difficulty difficulty;
    private Boolean isEquipmentRequired;
    private Date creationDate;
    private Integer duration;
    private Integer punctuation;




    public Routine(String name, String category, Difficulty difficulty, boolean isEquipmentRequired, Date creationDate, int punctuation, int duration) {

        if ( punctuation > 10 || punctuation < 1 )
            throw new IllegalArgumentException("Routine punctuation must be between 1 and 10");

        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.isEquipmentRequired = isEquipmentRequired;
        this.creationDate = creationDate;
        this.punctuation = punctuation;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isEquipmentRequired() {
        return isEquipmentRequired;
    }

    public void setIsEquipmentRequired(boolean equipment) {
        this.isEquipmentRequired = equipment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
    }

    public int getDuration(){
        return this.duration;
    }


    @Override
    public List<CardCaption> getCaptions() {
        ArrayList<CardCaption> al = new ArrayList<>();
        al.add(new CardCaption("title", "Nombre", name));
        al.add(new CardCaption("subtitle1", "Fecha", creationDate.toString()));
        al.add(new CardCaption("subtitle2", "Categoría", category));
        al.add(new CardCaption("subtitle3", "Equipación?", Boolean.toString(isEquipmentRequired)));
        al.add(new CardCaption("subtitle4", "Dificultad", difficulty.toString()));
        al.add(new CardCaption("subtitle5", "Duración", Integer.toString(duration)+"'"));
        return al;
    }

    // Cycles
    ArrayList<Cycle> cycles = new ArrayList<>();

    public void addCycle(Cycle cycle) {
        cycles.add(cycle);
    }

    public List<Cycle> getCycles() {
        return cycles;
    }

}