package com.example.entrenapp.bodyActivity;

import android.util.Range;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrenapp.apiClasses.Routine;

public class FilterViewModel extends ViewModel {

    private MutableLiveData<Routine.Difficulty> difficulty = new MutableLiveData<>();
    private MutableLiveData<Range> duration = new MutableLiveData<>();
    private MutableLiveData<Boolean> Equipment = new MutableLiveData<>();
    private MutableLiveData<String> Sport =new  MutableLiveData<>();

    public LiveData<Routine.Difficulty> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Routine.Difficulty difficulty) {
        this.difficulty.setValue(difficulty);
    }

    public LiveData<Range> getDuration() {
        return duration;
    }

    public void setDuration(Range duration) {
        this.duration.setValue(duration);
    }

    public LiveData<Boolean> isEquipment() {
        return Equipment;
    }

    public void setEquipment(boolean equipment) {
        Equipment.setValue(equipment);
    }

    public LiveData<String> getSport() {
        return Sport;
    }

    public void setSport(String sport) {
        Sport.setValue(sport);
    }
}
