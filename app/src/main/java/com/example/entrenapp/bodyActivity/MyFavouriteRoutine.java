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
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentMyFavouriteRoutineBinding;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MyFavouriteRoutine extends FragmentRoutine {

    private FragmentMyFavouriteRoutineBinding binding;


    @Override
    public void onDetach() {
        super.onDetach();
        filterViewModel.setDifficulty(null);
        filterViewModel.setDuration(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.favourite = true;
        binding = FragmentMyFavouriteRoutineBinding.inflate(inflater, container, false);
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        filterViewModel.getDuration().observe(getViewLifecycleOwner(), range -> initializeFilteredRoutine());
        filterViewModel.getDifficulty().observe(getViewLifecycleOwner(), difficulty -> initializeFilteredRoutine());
        return binding.getRoot();
    }

    @Override
    public void fillRoutines(){
        MyFavouriteRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class);
        viewModel.getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routine -> responseViewModel(routine));
    }



    @Override
    public void updateRecyclerView() {
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter1;
        if(datasetFiltered.size()>0){
            adapter1 = new CardAdapter(datasetFiltered, R.layout.extense_square_card, getActivity(),onNoteListener);
        }else {
            adapter1 = new CardAdapter(dataset, R.layout.extense_square_card, getActivity(),onNoteListener);
        }
        binding.routineRecyclerView.setAdapter(adapter1);
    }

}