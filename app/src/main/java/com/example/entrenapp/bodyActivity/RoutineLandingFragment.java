package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineLandingBinding;
import com.example.entrenapp.repository.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RoutineLandingFragment extends FragmentRoutine {

    private FragmentRoutineLandingBinding binding;
    private List<Routine> favouriteRoutines = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRoutineLandingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(binding.recommendedRoutinesRecyclerView);
    }

    @Override
    public void fillRoutines(){

        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routines -> {
            if(routines.size() > 0  && favouriteRoutines.size() == routines.size()-1) {
                favouriteRoutines.add(routines.get(routines.size()-1));
            }else{
                if(routines.size() >= 0 ){
                    favouriteRoutines = new ArrayList<>();
                    for(Routine routineItem : routines)
                        favouriteRoutines.add(routineItem);
                }
            }

        });


        RoutineLandingViewModel viewModel = new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class);
        viewModel.getOtherRoutines().observe(getViewLifecycleOwner(), routine -> {
                responseViewModel(routine.stream().filter(routine1 -> !favouriteRoutines.contains(routine1)).collect(Collectors.toList()));
        });
    }


    @Override
    public void updateRecyclerView() {
        binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter1 = new CardAdapter(dataset, R.layout.extense_square_card, getActivity(),onNoteListener);
        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);
        binding.recommendedRoutinesRecyclerView.scrollToPosition(2);
        binding.recommendedRoutinesRecyclerView.smoothScrollBy(1, 0);
    }

}