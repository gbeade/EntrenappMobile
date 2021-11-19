package com.example.entrenapp.bodyActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.entrenapp.App;
import com.example.entrenapp.DescriptionFragments.DescriptionActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class FragmentRoutine extends Fragment implements CardAdapter.ViewHolder.OnNoteListener{
    protected List<Routine> dataset;
    protected App app ;
    protected CardAdapter.ViewHolder.OnNoteListener onNoteListener;
    protected boolean favourite = false;
    protected boolean isfavouriteable= true;
    protected List<Routine> datasetFiltered = null ;
    protected FilterViewModel filterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        fillRoutines();
    }

    abstract public void fillRoutines();
    abstract public void updateRecyclerView();


    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        onNoteListener = this;
        requireActivity().invalidateOptionsMenu();
        fillRoutines();
    }

    protected void responseViewModel(List<Routine> routine){
        if(routine == null)
            return;

        if(dataset == null || routine.size() < dataset.size()){
            dataset = new ArrayList<>();
        }


        for(Routine r : routine){
            if(!dataset.contains(r))
                dataset.add(r);
        }


//        dataset.sort(((BodyActivity) getActivity()).getComparator());
        initializeFilteredRoutine();
    }


    protected void initializeFilteredRoutine(){
        datasetFiltered = new ArrayList<>();
        if(dataset == null)
            return;

        for(Cardable c : dataset){
            datasetFiltered.add((Routine) c);
        }

        if(filterViewModel.getDuration().getValue() != null){
            datasetFiltered = datasetFiltered.stream().filter(routine -> {
                return filterViewModel.getDuration().getValue().contains(routine.getDuration());
            }).collect(Collectors.toList());
        }

        if(filterViewModel.getDifficulty().getValue() != null){
            datasetFiltered = datasetFiltered.stream().filter(routine -> routine.getDifficulty() == filterViewModel.getDifficulty().getValue() ).collect(Collectors.toList());
        }

        if(filterViewModel.getEquipment() != null && filterViewModel.getEquipment().getValue() != null && filterViewModel.getEquipment().getValue().booleanValue()){
            datasetFiltered = datasetFiltered.stream().filter(routine -> routine.getMetadata().getEquipacion().booleanValue()).collect(Collectors.toList());
        }

        if(filterViewModel.getSport().getValue() != null){
            datasetFiltered = datasetFiltered.stream().filter(routine -> routine.getMetadata().getSport().equals(filterViewModel.getSport().getValue())).collect(Collectors.toList());
        }

        datasetFiltered.sort(((BodyActivity)getActivity()).getComparator());
        updateRecyclerView();
    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem settItem = menu.findItem(R.id.action_settings);
        settItem.setVisible(true);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        MenuItem sort = menu.findItem(R.id.action_sort);
        sort.setVisible(true);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        if(datasetFiltered == null)
            intent.putExtra("Routine", this.dataset.get(position));
        else
            intent.putExtra("Routine", this.datasetFiltered.get(position));

        intent.putExtra("Favourite",this.favourite);
        intent.putExtra("IsFavouritable",this.isfavouriteable);
        new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class).clear();
        new ViewModelProvider(getActivity()).get(MyRoutineViewModel.class).clear();
        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).clear();
        new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class).clear();
        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).clear();


        startActivity(intent);
    }

}
