package com.example.entrenapp.apiClasses;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.Nullable;

import com.example.entrenapp.ContextSingleton;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Routine implements Cardable,Parcelable {

    protected Routine(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
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
        cycles = in.createTypedArrayList(Cycle.CREATOR);
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
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
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
        dest.writeTypedList(cycles);
    }

    public enum Difficulty {
        rookie("rookie"),
        beginner("beginner"),
        intermediate("intermediate"),
        advanced("advanced"),
        expert("expert");

        public final String label;

        Difficulty(String label) {
            this.label = label;
        }
    }

    private Integer id;
    private String name;
    private String category;
    private Difficulty difficulty;
    private Boolean isEquipmentRequired;
    private Date creationDate;
    private Integer duration;
    private Integer punctuation;
    private RoutineAPI.RoutineMetadata metadata;




    public Routine(Integer id, String name, String category, Difficulty difficulty, boolean isEquipmentRequired, Date creationDate, int punctuation, int duration,RoutineAPI.RoutineMetadata metadata) {

        if ( punctuation > 10 || punctuation < 0 )
            throw new IllegalArgumentException("Routine punctuation must be between 1 and 10");

        this.id = id;
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.isEquipmentRequired = isEquipmentRequired;
        this.creationDate = creationDate;
        this.punctuation = punctuation;
        this.duration = duration;
        this.metadata = metadata;
    }

    public RoutineAPI.RoutineMetadata getMetadata(){
        return metadata;
    }

    public Integer getId() { return id; }

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
        Context context = ContextSingleton.getContext();
        al.add(new CardCaption("title", context.getString(R.string.name), name));
        al.add(new CardCaption("subtitle1", context.getString(R.string.date), creationDate.toString()));
        al.add(new CardCaption("subtitle2", context.getString(R.string.category), category));
        al.add(new CardCaption("subtitle3",context.getString(R.string.equipacion), isEquipmentRequired?context.getString(R.string.yes):context.getString(R.string.no)));
        al.add(new CardCaption("subtitle4", context.getString(R.string.difficulty), difficulty.toString()));
        al.add(new CardCaption("subtitle5", context.getString(R.string.duraci_n), duration +"'"));
        return al;
    }

    public void cleanCycle(){
        this.cycles = new ArrayList<>();
    }

    // Cycles
    ArrayList<Cycle> cycles = new ArrayList<>();

    public void addCycle(Cycle cycle) {
        if(cycle==null)
            return;
        cycles.add(cycle);
    }

    public List<Cycle> getCycles() {
        return cycles;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this)
            return true;
        if( ! (obj instanceof Routine))
            return false;
        Routine other = (Routine) obj;
        return other.getId() == this.id;
    }

}