package com.example.entrenapp.bodyActivity;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRoutineLandingBinding.inflate(inflater, container, false);
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);

        filterViewModel.getDuration().observe(getViewLifecycleOwner(), range -> initializeFilteredRoutine());
        filterViewModel.getDifficulty().observe(getViewLifecycleOwner(), difficulty -> initializeFilteredRoutine());
        filterViewModel.getEquipment().observe(getViewLifecycleOwner(), aBoolean -> initializeFilteredRoutine());
        filterViewModel.getSport().observe(getViewLifecycleOwner(), s -> initializeFilteredRoutine());

        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routines -> {
            if(routines == null)
                return ;

            for(Routine r : routines)
                if(!favouriteRoutines.contains(r))
                    favouriteRoutines.add(r);
        });
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(filterViewModel != null) {
            filterViewModel.setDifficulty(null);
            filterViewModel.setDuration(null);
            filterViewModel.setEquipment(false);
            filterViewModel.setSport(null);
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(binding.recommendedRoutinesRecyclerView);
    }

    @Override
    public void fillRoutines(){
        RoutineLandingViewModel viewModel = new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class);
        viewModel.getOtherRoutines().observe(getViewLifecycleOwner(), routine -> {
            if(routine!=null)
               responseViewModel(routine.stream().filter(routine1 -> !favouriteRoutines.contains(routine1)).collect(Collectors.toList()));
        });
    }


    @Override
    public void updateRecyclerView() {
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        else binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));        RecyclerView.Adapter adapter1;
        adapter1 = new CardAdapter(datasetFiltered, R.layout.extense_square_card, getActivity(),onNoteListener);
        if(datasetFiltered.size() == 0 ){
            Log.e("No hay","Resultados");
        }
        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);
    }

}