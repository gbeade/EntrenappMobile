package com.example.entrenapp.bodyActivity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.entrenapp.R;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineLandingBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoutineLandingFragment extends FragmentRoutine {

    private FragmentRoutineLandingBinding binding;
    private List<Routine> favouriteRoutines = new ArrayList<>();
    private List<Routine> datasetFiltered ;
    private FilterViewModel filterViewModel;

    @Override
    protected void initializeFilteredRoutine(){
         datasetFiltered = new ArrayList<>();
        for(Cardable c : dataset){
            datasetFiltered.add((Routine) c);
        }

        if(filterViewModel.getDuration().getValue() != null){
            datasetFiltered = datasetFiltered.stream().filter(routine -> {
                return filterViewModel.getDuration().getValue().contains(routine.getDuration());
            }).collect(Collectors.toList());
        }

        if(filterViewModel.getDifficulty().getValue() != null){
            datasetFiltered = datasetFiltered.stream().filter(routine -> routine.getDifficulty() == filterViewModel.getDifficulty().getValue() ).collect(Collectors.toList());
        }

        updateRecyclerView();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRoutineLandingBinding.inflate(inflater, container, false);
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);

        filterViewModel.getDuration().observe(getViewLifecycleOwner(), range -> initializeFilteredRoutine());
        filterViewModel.getDifficulty().observe(getViewLifecycleOwner(), difficulty -> initializeFilteredRoutine());
        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routines -> {
            for(Routine r : routines)
                if(!favouriteRoutines.contains(r))
                    favouriteRoutines.add(r);
        });
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        initializeFilteredRoutine();
        updateRecyclerView();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(binding.recommendedRoutinesRecyclerView);
        initializeFilteredRoutine();
        updateRecyclerView();

    }

    @Override
    public void fillRoutines(){
        RoutineLandingViewModel viewModel = new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class);
        viewModel.getOtherRoutines().observe(getViewLifecycleOwner(), routine -> {
            responseViewModel(routine.stream().filter(routine1 -> !favouriteRoutines.contains(routine1)).collect(Collectors.toList()));
        });
    }


    @Override
    public void updateRecyclerView() {
        binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter1;
        if(datasetFiltered.size()>0){
            adapter1 = new CardAdapter(datasetFiltered, R.layout.extense_square_card, getActivity(),onNoteListener);
        }else {
            adapter1 = new CardAdapter(dataset, R.layout.extense_square_card, getActivity(),onNoteListener);
        }
        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);
    }

}