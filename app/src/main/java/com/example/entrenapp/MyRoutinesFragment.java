package com.example.entrenapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.databinding.FragmentMyRoutinesBinding;
import com.example.entrenapp.databinding.FragmentRoutineLandingBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MyRoutinesFragment extends Fragment {
    private FragmentMyRoutinesBinding binding;
    private ArrayList<Cardable> dataset = new ArrayList<>();

    private Predicate<Cardable> filterFun = r -> true;

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

        fillRoutines();
        RecyclerView.Adapter adapter = new CardAdapter(this.dataset.stream().filter(filterFun).collect(Collectors.toList()), R.layout.extense_square_card, getActivity());
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.routineRecyclerView.setAdapter(adapter);
    }


    private void fillRoutines(){
        dataset.add(new Routine("Pecho Plano yyy", "Pecho", Routine.Difficulty.XTREME, false, new Date(), 4, 25));
        dataset.add(new Routine("Pecho Plano xxx", "Brazos", Routine.Difficulty.XTREME, true, new Date(), 3, 56));
        dataset.add(new Routine("Super atletico", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 5, 2));
        dataset.add(new Routine("Anti grasa 2.0", "Atletico", Routine.Difficulty.XTREME, true, new Date(), 8, 25));
        dataset.add(new Routine("Sprints 21", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 6, 40));
        dataset.add(new Routine("Picar", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 10, 25));
    }
}