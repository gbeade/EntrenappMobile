package com.example.entrenapp.bodyActivity;

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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class FragmentRoutine extends Fragment implements CardAdapter.ViewHolder.OnNoteListener{
    protected ArrayList<Cardable> dataset = new ArrayList<>();
    protected App app ;
    protected CardAdapter.ViewHolder.OnNoteListener onNoteListener;
    protected boolean favourite = false;
    protected boolean isfavouriteable= true;
    protected List<Routine> datasetFiltered = null ;
    protected FilterViewModel filterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataset = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    abstract public void fillRoutines();

    abstract public void updateRecyclerView();

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        if(datasetFiltered == null)
            intent.putExtra("Routine",  (Parcelable) this.dataset.get(position));
        else
            intent.putExtra("Routine", this.datasetFiltered.get(position));

        intent.putExtra("Favourite",this.favourite);
        intent.putExtra("IsFavouritable",this.isfavouriteable);
        new ViewModelProvider(getActivity()).get(RoutineLandingViewModel.class).clear();
        new ViewModelProvider(getActivity()).get(MyRoutineViewModel.class).clear();
        new ViewModelProvider(getActivity()).get(MyFavouriteRoutineViewModel.class).clear();
        this.dataset= new ArrayList<>();
   //     getActivity().getViewModelStore().clear();
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializeFilteredRoutine();
        updateRecyclerView();
        fillRoutines();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        onNoteListener = this;
        requireActivity().invalidateOptionsMenu();
        initializeFilteredRoutine();
        updateRecyclerView();
    }

    protected void responseViewModel(List<Routine> routine){
        if(routine== null)
            return;

        for(Routine r : routine){
            if(!dataset.contains(r))
                dataset.add(r);
        }

        initializeFilteredRoutine();
        updateRecyclerView();
    }


    protected void initializeFilteredRoutine(){
        datasetFiltered = new ArrayList<>();
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
            Log.e("deporte",filterViewModel.getSport().getValue());
            datasetFiltered = datasetFiltered.stream().filter(routine -> routine.getMetadata().getSport().equals(filterViewModel.getSport().getValue())).collect(Collectors.toList());
        }

        updateRecyclerView();
    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
    }





}
