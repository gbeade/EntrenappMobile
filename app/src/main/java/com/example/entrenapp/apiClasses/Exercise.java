package com.example.entrenapp.apiClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.List;

public class Exercise implements Cardable, Parcelable {

    private int id;
    private String name;
    private String type;
    private int duration;

    protected Exercise(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        duration = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(duration);
    }
}
