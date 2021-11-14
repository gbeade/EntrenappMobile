package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.bodyActivity.FragmentRoutine;
import com.example.entrenapp.databinding.FragmentMyFavouriteRoutineBinding;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.repository.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class MyFavouriteRoutine extends FragmentRoutine {

    private FragmentMyFavouriteRoutineBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.favourite = true;
        binding = FragmentMyFavouriteRoutineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void fillRoutines(){
        MyFavouriteRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class);
        viewModel.getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routine -> responseViewModel(routine));
    }

    @Override
    public void updateRecyclerView() {
        RecyclerView.Adapter adapter = new CardAdapter(dataset.stream().filter(filterFun).collect(Collectors.toList()), R.layout.extense_square_card, getActivity(),onNoteListener);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.routineRecyclerView.setAdapter(adapter);
    }

}