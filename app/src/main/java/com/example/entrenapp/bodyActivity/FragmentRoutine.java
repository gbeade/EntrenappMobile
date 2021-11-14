package com.example.entrenapp.bodyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.entrenapp.App;
import com.example.entrenapp.DescriptionFragments.DescriptionActivity;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.RoutineAPI;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import com.example.entrenapp.repository.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;

public abstract class FragmentRoutine extends Fragment implements CardAdapter.ViewHolder.OnNoteListener{
    protected ArrayList<Cardable> dataset = new ArrayList<>();
    protected Predicate<Cardable> filterFun = r -> true;
    protected App app ;
    protected Integer UserId;
    protected CardAdapter.ViewHolder.OnNoteListener onNoteListener;


     abstract public void fillRoutines();



    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        intent.putExtra("Routine",  (Parcelable) this.dataset.get(position));
        startActivity(intent);

    }
}
