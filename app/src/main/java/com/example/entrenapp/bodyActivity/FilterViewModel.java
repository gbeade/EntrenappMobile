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
    private MutableLiveData<String> name = new MutableLiveData<>();

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
        Equipment.setValue(new Boolean(equipment));
    }

    public LiveData<Boolean> getEquipment(){return Equipment;}

    public LiveData<String> getSport() {
        return Sport;
    }

    public void setSport(String sport) {
        Sport.setValue(sport);
    }

    public void addLetter(String letter){
        this.name.setValue(letter);
    }

    public void clearName(){
        this.name = new MutableLiveData<>();
    }

    public MutableLiveData<String> getName(){
        return this.name;
    }

    public void clear(){
        this.difficulty.setValue(null);
        this.duration.setValue(null);
        this.Equipment.setValue(null);
        this.Sport.setValue(null);
    }

}
