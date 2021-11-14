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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.entrenapp.App;
import com.example.entrenapp.api.model.RoutineCycle;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineDescriptionBinding;
import com.example.entrenapp.api.model.ApiExercise;

import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.recyclerView.CycleAdapter;
import com.example.entrenapp.repository.Status;

import java.util.List;


public class FragmentRoutineDescription extends Fragment {

    private Routine routine;
    FragmentRoutineDescriptionBinding binding;
    private App app;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.routine = getArguments().getParcelable("Routine");

        binding.routineTitle.setText(this.routine.getName());
        binding.duration.setText("Duracion: "+this.routine.getDuration()+" minutos");

        fillRoutine();

        binding.btnTrain.setOnClickListener(v -> train());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoutineDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private void train(){ //llamado a navController
        Intent intent = new Intent(getActivity(), ExecuteRoutineActivity.class);
        intent.putExtra("Routine", this.routine);
        startActivity(intent);
    }

    private void fillRoutine(){
        FragmentRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(FragmentRoutineViewModel.class);
        viewModel.setRoutineId(this.routine);

        viewModel.getCycle().observe(getViewLifecycleOwner(), new Observer<List<Cycle>>() {
            @Override
            public void onChanged(List<Cycle> cycle) {
                routine.cleanCycle();
                for(Cycle cycleItem : cycle)
                    routine.addCycle(cycleItem);

                RecyclerView.Adapter adapter = new CycleAdapter(routine.getCycles(),getActivity());
                binding.routineDescriptionCyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                binding.routineDescriptionCyclerView.setAdapter(adapter);
            }
        });
    }

}