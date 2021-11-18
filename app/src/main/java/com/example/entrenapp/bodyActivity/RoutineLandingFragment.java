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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.entrenapp.R;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineLandingBinding;
import com.example.entrenapp.repository.UserSession;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoutineLandingFragment extends FragmentRoutine {

    private FragmentRoutineLandingBinding binding;
    private List<Routine> favouriteRoutines = new ArrayList<>();

    private Routine getNextBestRoutine(Routine routine) {
        for (Routine r: favouriteRoutines) {
            if ( r.getCategory().equals(routine.getCategory())) {
                return r;
            }
        }
        for (Routine r: favouriteRoutines) {
            if ( r.getDifficulty().equals(routine.getDifficulty())) {
                return r;
            }
        }
        // Todo: change to null
        return new Routine(1000, "NO ROUTINE", "Pecho", Routine.Difficulty.rookie, false, new Date(), 1, 2, null);
    }

    PopupWindow popupWindow;
    private void showPopup() {

        Routine last = UserSession.getLastExecutedRoutine();
        Routine nextBest = getNextBestRoutine(last);
        Log.i("NEXTBEST", ( nextBest == null ? "-" : nextBest.getName()));
        Log.i("LASTRUN", ( last == null ? "-" : last.getName()));

        if ( nextBest == null) return;


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

        // Fetch popup view
        View popupView = inflater.inflate(R.layout.popup_routine_recommendation, null);

        // create the popup window
        int width = (int)getResources().getDisplayMetrics().density * 450;
        int height = (int)getResources().getDisplayMetrics().density * 550;
        popupWindow = new PopupWindow(popupView, width, height, true);


        ((TextView)popupView.findViewById(R.id.routineName)).setText(last.getName());


        View card = popupView.findViewById(R.id.includeRoutineCard);
        ((TextView)(card.findViewById(R.id.title))).setText(nextBest.getName());
        ((TextView)(card.findViewById(R.id.subtitle1_header))).setText(getString(R.string.duraci_n));
        ((TextView)(card.findViewById(R.id.subtitle1))).setText(nextBest.getDuration()+"'");
        ((TextView)(card.findViewById(R.id.subtitle2_header))).setText(getString(R.string.category));
        ((TextView)(card.findViewById(R.id.subtitle2))).setText(nextBest.getCategory());

        card.setOnClickListener( v->Log.i("MESSG", "TAKE ME TO THE ROUTINE"));

        popupView.findViewById(R.id.btn_return).setOnClickListener((v)->popupWindow.dismiss());

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(getActivity().findViewById(R.id.bodyContainer), Gravity.CENTER, 0, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRoutineLandingBinding.inflate(inflater, container, false);
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);

        filterViewModel.getDuration().observe(getViewLifecycleOwner(), range -> initializeFilteredRoutine());
        filterViewModel.getDifficulty().observe(getViewLifecycleOwner(), difficulty -> initializeFilteredRoutine());
        filterViewModel.getEquipment().observe(getViewLifecycleOwner(), aBoolean -> initializeFilteredRoutine());
        filterViewModel.getSport().observe(getViewLifecycleOwner(), s -> initializeFilteredRoutine());

        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).getMyFavouriteRoutines().observe(getViewLifecycleOwner(), routines -> {
            if(routines == null)
                return ;

            for(Routine r : routines)
                if(!favouriteRoutines.contains(r))
                    favouriteRoutines.add(r);
        });

        return binding.getRoot();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(binding.recommendedRoutinesRecyclerView);
    }

    @Override
    public void fillRoutines(){
        RoutineLandingViewModel viewModel = new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class);
        viewModel.getOtherRoutines().observe(getViewLifecycleOwner(), routine -> {
            if(routine!=null)
               responseViewModel(routine.stream().filter(routine1 -> !favouriteRoutines.contains(routine1)).collect(Collectors.toList()));
        });

        if (UserSession.getLastExecutedRoutine() != null) {
            showPopup();
            UserSession.setLastExecutedRoutine(null);
        }
    }


    @Override
    public void updateRecyclerView() {
        if( dataset.size() == 0 || datasetFiltered.size() == 0 ){
            getActivity().findViewById(R.id.noRoutine3).setVisibility(View.VISIBLE);
            return;
        }
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        else binding.recommendedRoutinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        getActivity().findViewById(R.id.noRoutine3).setVisibility(View.GONE);

        RecyclerView.Adapter adapter1;
        adapter1 = new CardAdapter(datasetFiltered, R.layout.extense_square_card, getActivity(),onNoteListener);
        binding.recommendedRoutinesRecyclerView.setAdapter(adapter1);
    }

}