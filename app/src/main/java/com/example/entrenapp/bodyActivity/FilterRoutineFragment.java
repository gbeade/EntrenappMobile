package com.example.entrenapp.bodyActivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentFilterRoutineBinding;


public class FilterRoutineFragment extends Fragment {

    private Routine.Difficulty difficulty ;
    private Range duration;
    private FragmentFilterRoutineBinding binding;
    private Button difficultyButton;
    private Button difficultyButtonWhite;
    private Button durationButtonWhite;
    private Button durationButton;


    private void show(Button button){
        button.setVisibility(View.VISIBLE);
    }

    private void hide(Button button){
        button.setVisibility(View.GONE);
    }


    private void difficultyWhiteHandler(Button buttonWhite , Button button , Routine.Difficulty difficulty){
        if(this.difficultyButtonWhite != null)
            show(this.difficultyButtonWhite);
        if(this.difficultyButton != null)
            hide(this.difficultyButton);
        this.difficultyButtonWhite=buttonWhite;
        this.difficultyButton=button;
        show(this.difficultyButton);
        hide(this.difficultyButtonWhite);
        this.difficulty = difficulty;
    }

    private void durationWhiteHandler(Button buttonWhite , Button button , Range duration){
        if(this.durationButtonWhite != null)
            show(this.durationButtonWhite);
        if(this.durationButton != null)
            hide(this.durationButton);
        this.durationButtonWhite=buttonWhite;
        this.durationButton=button;
        show(this.durationButton);
        hide(this.durationButtonWhite);
        this.duration = duration;
    }

    private void durationYellowHandler(){
        if(this.durationButton != null)
            hide(this.durationButton);
        if(this.durationButtonWhite != null)
            show(this.durationButtonWhite);
        this.duration = null;

    }

    private void difficultyYellowHandler(){
        if(this.difficultyButton != null)
            hide(this.difficultyButton);
        if(this.difficultyButtonWhite != null)
            show(this.difficultyButtonWhite);
        this.difficulty = null;
    }



    private FilterViewModel filter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().invalidateOptionsMenu();
        binding = FragmentFilterRoutineBinding.inflate(inflater,container,false);
        filter = new ViewModelProvider(getActivity()).get(FilterViewModel.class);

        binding.btnDifficultyAdvanced.setOnClickListener(v -> difficultyWhiteHandler(binding.btnDifficultyAdvanced,binding.btnDifficultyAdvancedYellow, Routine.Difficulty.expert));
        binding.btnDifficultyIntermediate.setOnClickListener(v -> difficultyWhiteHandler(binding.btnDifficultyIntermediate,binding.btnDifficultyIntermediateYellow, Routine.Difficulty.intermediate));
        binding.btnDifficultyBeginner.setOnClickListener(v -> difficultyWhiteHandler(binding.btnDifficultyBeginner,binding.btnDifficultyBeginnerYellow, Routine.Difficulty.rookie));
        binding.btnDifficultyAdvancedYellow.setOnClickListener(v -> difficultyYellowHandler());
        binding.btnDifficultyIntermediateYellow.setOnClickListener(v -> difficultyYellowHandler());
        binding.btnDifficultyBeginnerYellow.setOnClickListener(v -> difficultyYellowHandler());

        binding.btnInterval1.setOnClickListener(v -> durationWhiteHandler(binding.btnInterval1,binding.btnInterval1Yellow,new Range(15,30)));
        binding.btnInterval2.setOnClickListener(v -> durationWhiteHandler(binding.btnInterval2,binding.btnInterval2Yellow,new Range(30,45)));
        binding.btnInterval3.setOnClickListener(v -> durationWhiteHandler(binding.btnInterval3,binding.btnInterval3Yellow,new Range(45,60)));
        binding.btnInterval1Yellow.setOnClickListener(v -> durationYellowHandler());
        binding.btnInterval2Yellow.setOnClickListener(v -> durationYellowHandler());
        binding.btnInterval3Yellow.setOnClickListener(v -> durationYellowHandler());

        binding.btnSave.setOnClickListener(v -> {
            filter.setDuration(duration);
            filter.setDifficulty(difficulty);
            filter.setEquipment(binding.checkBox.isChecked());
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigateUp();
        });

        if(filter.getDuration().getValue() !=null){
            if(filter.getDuration().getValue().equals(new Range(15,30))){
                this.durationButtonWhite=binding.btnInterval1;
                this.durationButton=binding.btnInterval1Yellow;
                this.duration = new Range(15,30);
            }else if(filter.getDuration().getValue().equals(new Range(30,45))){
                this.durationButtonWhite=binding.btnInterval2;
                this.durationButton=binding.btnInterval2Yellow;
                this.duration = new Range(30,45);
            }else {
                this.durationButtonWhite=binding.btnInterval3;
                this.durationButton=binding.btnInterval3Yellow;
                this.duration = new Range(45,60);
            }
            show(this.durationButton);
            hide(this.durationButtonWhite);

        }

        if(filter.getDifficulty().getValue() != null){
            if(filter.getDifficulty().getValue().equals(Routine.Difficulty.expert)){
                this.difficultyButtonWhite = binding.btnDifficultyAdvanced;
                this.difficultyButton = binding.btnDifficultyAdvancedYellow;
                this.difficulty = Routine.Difficulty.expert;
            }else if(filter.getDifficulty().getValue().equals(Routine.Difficulty.intermediate)){
                this.difficultyButtonWhite = binding.btnDifficultyIntermediate;
                this.difficultyButton = binding.btnDifficultyIntermediateYellow;
                this.difficulty = Routine.Difficulty.intermediate;
            }else{
                this.difficultyButtonWhite= binding.btnDifficultyBeginner;
                this.difficultyButton = binding.btnDifficultyBeginnerYellow;
                this.difficulty = Routine.Difficulty.rookie;
            }
                show(this.difficultyButton);
                hide(this.difficultyButtonWhite);
        }

        if(filter.getEquipment()!=null && filter.getEquipment().getValue()!= null&& filter.getEquipment().getValue().booleanValue()){
            binding.checkBox.setChecked(true);
        }


        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        MenuItem back = menu.findItem(R.id.action_back);
        back.setVisible(true);
    }


}