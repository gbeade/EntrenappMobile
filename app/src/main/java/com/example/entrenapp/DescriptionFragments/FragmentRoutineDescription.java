package com.example.entrenapp.DescriptionFragments;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.entrenapp.App;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineDescriptionBinding;


import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.recyclerView.CycleAdapter;
import com.example.entrenapp.repository.Resource;


import java.util.List;


public class FragmentRoutineDescription extends Fragment {

    private Routine routine;
    private boolean favourite;
    private boolean isFavouritable;
    FragmentRoutineDescriptionBinding binding;
    private App app;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.routine = getArguments().getParcelable("Routine");
        this.favourite = getArguments().getBoolean("Favourite");
        this.isFavouritable = getArguments().getBoolean("IsFavouritable");


        app = (App) getActivity().getApplication();
        binding.routineTitle.setText(this.routine.getName());
        binding.duration.setText("Duración: " + this.routine.getDuration() + " minutos");

        if (this.isFavouritable) {
            if (this.favourite) {
                binding.addFavourite.setVisibility(View.GONE);
                binding.removeFavourite.setVisibility(View.VISIBLE);
            } else {
                binding.addFavourite.setVisibility(View.VISIBLE);
                binding.removeFavourite.setVisibility(View.GONE);
            }


            binding.addFavourite.setOnClickListener(v -> app.getRoutineRepository().setFavourite(routine.getId()).observe(getViewLifecycleOwner(), voidResource -> {
                binding.addFavourite.setVisibility(View.GONE);
                binding.removeFavourite.setVisibility(View.VISIBLE);
            }));

            binding.removeFavourite.setOnClickListener(v -> app.getRoutineRepository().deleteFavourite(routine.getId()).observe(getViewLifecycleOwner(), voidResource -> {
                binding.removeFavourite.setVisibility(View.GONE);
                binding.addFavourite.setVisibility(View.VISIBLE);
            }));
        }else{
            binding.addFavourite.setVisibility(View.GONE);
            binding.removeFavourite.setVisibility(View.GONE);
        }

        fillRoutine();
        binding.btnTrain.setOnClickListener(v -> train());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoutineDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private void train(){
        Intent intent = new Intent(getActivity(), ExecuteRoutineActivity.class);
        intent.putExtra("Routine", this.routine);
        startActivity(intent);
    }

    private void fillRoutine(){
        FragmentRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(FragmentRoutineViewModel.class);
        viewModel.setRoutineId(this.routine);

        viewModel.getCycle().observe(getViewLifecycleOwner(), cycle -> {

            if(cycle.size() > 0 && routine.getCycles().size() == cycle.size()-1)
              routine.addCycle( cycle.get(cycle.size()-1));


            RecyclerView.Adapter adapter = new CycleAdapter(routine.getCycles(),getActivity());
            binding.routineDescriptionCyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            binding.routineDescriptionCyclerView.setAdapter(adapter);
        });
    }

}