package com.example.entrenapp.apiClasses;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.entrenapp.ContextSingleton;
import com.example.entrenapp.R;
import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.List;

public class Exercise implements Cardable, Parcelable {

    private int id;
    private String name;
    private String type;
    private int duration;
    private int repetitions = 1;

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
        Context context = ContextSingleton.getContext();
        al.add(new CardCaption("title", context.getString(R.string.name), name));
        al.add(new CardCaption("subtitle1",context.getString(R.string.duraci_n), Integer.toString(duration)));
        al.add(new CardCaption("subtitle2", context.getString(R.string.type), type));
        return al;
    }




    @NonNull
    @Override
    public String toString() {
        return this.id+" "+this.name+" "+this.duration+" "+this.repetitions;
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
        dest.writeInt(repetitions);
    }
}
