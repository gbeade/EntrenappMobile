package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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
import com.example.entrenapp.recyclerView.CardAdapter;

import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentMyRoutinesBinding;
import com.example.entrenapp.repository.Resource;

import java.util.Date;
import java.util.stream.Collectors;


public class MyRoutinesFragment extends FragmentRoutine {
    private FragmentMyRoutinesBinding binding;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMyRoutinesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        onNoteListener = this;
        fillRoutines();

    }

    @Override
    public void fillRoutines(){
        MyRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(MyRoutineViewModel.class);
        viewModel.getMyRoutines().observe(getViewLifecycleOwner(), new Observer<Routine>() {
            @Override
            public void onChanged(Routine routine) {
                dataset.add(routine);
                RecyclerView.Adapter adapter = new CardAdapter(dataset.stream().filter(filterFun).collect(Collectors.toList()), R.layout.extense_square_card, getActivity(),onNoteListener);
                binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.routineRecyclerView.setAdapter(adapter);
            }
        });


    }

}