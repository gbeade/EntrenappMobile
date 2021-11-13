package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
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

import java.util.Date;
import java.util.stream.Collectors;


public class MyFavouriteRoutine extends FragmentRoutine {

    private FragmentMyFavouriteRoutineBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        onNoteListener = this;
        fillRoutines();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyFavouriteRoutineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void fillRoutines(){
        app = (App) getActivity().getApplication();
        app.getRoutineRepository().getMyFavouriteRoutines().observe(getViewLifecycleOwner(), new Observer<Resource<PagedList<RoutineAPI>>>() {
            @Override
            public void onChanged(Resource<PagedList<RoutineAPI>> pagedListResource) {
                if(pagedListResource.getData() != null && pagedListResource.getData().getContent().size() > 0){
                    for(RoutineAPI routine : pagedListResource.getData().getContent()){
                        dataset.add(new Routine(routine.getName(),routine.getMetadata().getSport(), Routine.Difficulty.valueOf(routine.getDifficulty()),routine.getMetadata().getEquipacion(),new Date(routine.getDate()),routine.getScore(),routine.getMetadata().getDuracion()));
                    }

                    //recycler view
                    RecyclerView.Adapter adapter = new CardAdapter(dataset.stream().filter(filterFun).collect(Collectors.toList()), R.layout.extense_square_card, getActivity(),onNoteListener);
                    binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.routineRecyclerView.setAdapter(adapter);
                }
            }
        });
    }

}