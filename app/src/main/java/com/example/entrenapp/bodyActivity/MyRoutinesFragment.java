package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.R;
import com.example.entrenapp.recyclerView.CardAdapter;

import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentMyRoutinesBinding;
import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class MyRoutinesFragment extends FragmentRoutine {
    private FragmentMyRoutinesBinding binding;


    @Override
    protected void initializeFilteredRoutine(){
        datasetFiltered = new ArrayList<>();
        for(Cardable c : dataset){
            datasetFiltered.add((Routine) c);
        }

        if(filterViewModel.getName().getValue() != null){
            datasetFiltered = datasetFiltered.stream().filter(routine -> (routine.getName().startsWith(filterViewModel.getName().getValue())|routine.getName().contains(filterViewModel.getName().getValue()))).collect(Collectors.toList());
        }

        updateRecyclerView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.isfavouriteable = false;
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        filterViewModel.getName().observe(getViewLifecycleOwner(), s -> initializeFilteredRoutine());
        binding = FragmentMyRoutinesBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }




    @Override
    public void fillRoutines(){
        MyRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(MyRoutineViewModel.class);
        viewModel.getMyRoutines().observe(getViewLifecycleOwner(), routine -> responseViewModel(routine));
    }


    @Override
    public void updateRecyclerView() {
        // super.updateRecyclerView();
        if( dataset.size() == 0 || datasetFiltered.size() == 0 ){
            getActivity().findViewById(R.id.noRoutine2).setVisibility(View.VISIBLE);
            return;
        }
        getActivity().findViewById(R.id.noRoutine2).setVisibility(View.GONE);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter1;
        adapter1 = new CardAdapter(datasetFiltered, R.layout.extense_square_card, getActivity(),onNoteListener);
        binding.routineRecyclerView.setAdapter(adapter1);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem filter = menu.findItem(R.id.action_filter);
        filter.setVisible(false);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);
    }



}