package com.example.entrenapp.bodyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineLandingBinding;
import com.example.entrenapp.repository.Resource;

import java.util.ArrayList;
import java.util.Date;

public class RoutineLandingFragment extends FragmentRoutine {

    private FragmentRoutineLandingBinding binding;

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
        onNoteListener = this;
        app = (App) getActivity().getApplication();
        UserId = app.getPreferences().getUserId();
        fillRoutines();
    }

    @Override
    public void fillRoutines(){
            app = (App) getActivity().getApplication();
            app.getRoutineRepository().getRoutines().observe(getViewLifecycleOwner(), new Observer<Resource<PagedList<RoutineAPI>>>() {
                @Override
                public void onChanged(Resource<PagedList<RoutineAPI>> pagedListResource) {
                    if(pagedListResource.getData() != null && pagedListResource.getData().getContent().size() > 0){
                        for(RoutineAPI routine : pagedListResource.getData().getContent()){
                            if(! UserId.equals(routine.getUser().getId()) )
                                dataset.add(new Routine(routine.getName(),routine.getMetadata().getSport(), Routine.Difficulty.valueOf(routine.getDifficulty()),routine.getMetadata().getEquipacion(),new Date(routine.getDate()),routine.getScore(),routine.getMetadata().getDuracion()));
                        }

                        //recycler view
                        binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter1 = new CardAdapter(dataset, R.layout.extense_square_card, getActivity(),onNoteListener);
                        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);
                        SnapHelper snapHelper1 = new LinearSnapHelper();
                        snapHelper1.attachToRecyclerView(binding.recommendedRoutinesRecyclerView);
                        binding.recommendedRoutinesRecyclerView.scrollToPosition(2);
                        binding.recommendedRoutinesRecyclerView.smoothScrollBy(1, 0);

                    }
                }
            });

    }
}