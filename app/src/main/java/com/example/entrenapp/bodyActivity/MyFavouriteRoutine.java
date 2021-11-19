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
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentMyFavouriteRoutineBinding;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MyFavouriteRoutine extends FragmentRoutine {

    private FragmentMyFavouriteRoutineBinding binding;
    private MyFavouriteRoutineViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.routineRecyclerView);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.favourite = true;
        binding = FragmentMyFavouriteRoutineBinding.inflate(inflater, container, false);
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        filterViewModel.getDuration().observe(getViewLifecycleOwner(), range -> initializeFilteredRoutine());
        filterViewModel.getDifficulty().observe(getViewLifecycleOwner(), difficulty -> initializeFilteredRoutine());
        filterViewModel.getEquipment().observe(getViewLifecycleOwner(), aBoolean -> initializeFilteredRoutine());
        filterViewModel.getSport().observe(getViewLifecycleOwner(), s -> initializeFilteredRoutine());

        return binding.getRoot();
    }

    @Override
    public void fillRoutines(){
        viewModel = new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class);
        viewModel.getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routine -> responseViewModel(routine));
    }





    @Override
    public void updateRecyclerView() {
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        else
            binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        getActivity().findViewById(R.id.noRoutine1).setVisibility(View.GONE);
        RecyclerView.Adapter adapter1;
        adapter1 = new CardAdapter(datasetFiltered, R.layout.extense_square_card, getActivity(),onNoteListener);
        binding.routineRecyclerView.setAdapter(adapter1);

        if(datasetFiltered.size() == 0 ){
            getActivity().findViewById(R.id.noRoutine1).setVisibility(View.VISIBLE);
        }
    }

}