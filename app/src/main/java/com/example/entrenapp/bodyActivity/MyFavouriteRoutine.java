package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.R;
import com.example.entrenapp.bodyActivity.FragmentRoutine;
import com.example.entrenapp.databinding.FragmentMyFavouriteRoutineBinding;
import com.example.entrenapp.recyclerView.CardAdapter;

import java.util.stream.Collectors;


public class MyFavouriteRoutine extends FragmentRoutine {

    private FragmentMyFavouriteRoutineBinding binding;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        fillRoutines();
        RecyclerView.Adapter adapter = new CardAdapter(this.dataset.stream().filter(filterFun).collect(Collectors.toList()), R.layout.extense_square_card, getActivity(),this);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.routineRecyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyFavouriteRoutineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}