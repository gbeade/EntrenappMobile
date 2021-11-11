package com.example.entrenapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.entrenapp.databinding.FragmentRoutineLandingBinding;

import java.util.ArrayList;
import java.util.Date;

public class RoutineLandingFragment extends Fragment {

    private FragmentRoutineLandingBinding binding;
    private ArrayList<Cardable> dataset = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRoutineLandingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        fillRoutines();
        binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter1 = new CardAdapter(this.dataset, R.layout.extense_square_card, getActivity());
        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);
        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(binding.recommendedRoutinesRecyclerView);
        binding.recommendedRoutinesRecyclerView.scrollToPosition(2);
        binding.recommendedRoutinesRecyclerView.smoothScrollBy(1, 0);

        binding.myRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter2 = new CardAdapter(this.dataset, R.layout.summary_square_card, getActivity());
        binding.myRoutinesRecyclerView.setAdapter(adapter2);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(binding.myRoutinesRecyclerView);
        binding.myRoutinesRecyclerView.scrollToPosition(2);
        binding.myRoutinesRecyclerView.smoothScrollBy(1, 0);

        binding.favouriteRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapter3 = new CardAdapter(this.dataset, R.layout.summary_square_card, getActivity());
        binding.favouriteRoutinesRecyclerView.setAdapter(adapter3);
        SnapHelper snapHelper3 = new LinearSnapHelper();
        snapHelper3.attachToRecyclerView(binding.favouriteRoutinesRecyclerView);
        binding.favouriteRoutinesRecyclerView.scrollToPosition(2);
        binding.favouriteRoutinesRecyclerView.smoothScrollBy(1, 0);
    }


    private void fillRoutines(){
        for(int i = 0 ; i < 4 ; i++)
            dataset.add(new Routine("EjemploDeClase", "Brazos", Routine.Difficulty.XTREME, false, new Date(), 4, 10));
    }
}