package com.example.entrenapp.apiClasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cycle implements Parcelable {

    private int id;
    private String name;
    private String detail;
    private String type;
    private int order;
    private int repetitions;
    private List<Exercise> exercises;


    protected Cycle(Parcel in) {
        id = in.readInt();
        name = in.readString();
        detail = in.readString();
        type = in.readString();
        order = in.readInt();
        repetitions = in.readInt();
        exercises = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Creator<Cycle> CREATOR = new Creator<Cycle>() {
        @Override
        public Cycle createFromParcel(Parcel in) {
            return new Cycle(in);
        }

        @Override
        public Cycle[] newArray(int size) {
            return new Cycle[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public Cycle(int id, String name, String detail, String type, int order, int repetitions) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise e) {
        exercises.add(e);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }




    @NonNull
    @Override
    public String toString() {
        return this.id+" " + this.name+" "+this.detail+" "+this.type+" "+this.order+" "+this.repetitions+" "+ Arrays.toString(this.exercises.toArray());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this)
            return true;
        if(! (obj instanceof  Cycle))
            return false;
        Cycle other = (Cycle) obj;
        return this.id == other.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeString(type);
        dest.writeInt(order);
        dest.writeInt(repetitions);
        dest.writeTypedList(exercises);
    }
}
