package com.example.entrenapp.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.DescriptionFragments.ExerciseDescriptionActivity;
import com.example.entrenapp.DescriptionFragments.FragmentRoutineDescription;
import com.example.entrenapp.DescriptionFragments.FragmentRoutineDescriptionDirections;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Cycle;

import java.util.List;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.CycleVH> {
    private List<Cycle> cycles;
    private Context context;
    private View view;

    public CycleAdapter(List<Cycle> cycles , Context context){
        this.cycles=cycles;
        this.context=context;
    }


    @NonNull
    public CycleVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType ) {
         view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cycle_view_holder, viewGroup, false);

        return new CycleAdapter.CycleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleVH holder, int position) {
            holder.getTextView().setText(cycles.get(position).getName());
            RecyclerView.Adapter adapter = new CardAdapter(cycles.get(position).getExercises(), R.layout.summary_square_card, context, position1 -> {
                NavDirections action = FragmentRoutineDescriptionDirections.actionFragmentRoutineDescriptionToFragmentExerciseDescription(cycles.get(position).getExercises().get(position1));
                Navigation.findNavController(view).navigate(action);
            });
            holder.getRecyclerView().setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.getRecyclerView().setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public static class CycleVH extends RecyclerView.ViewHolder {

        private final TextView textView;
        private RecyclerView recyclerView;

        public CycleVH(View view){
            super(view);

            textView =  view.findViewById(R.id.cycle_text_view);
            recyclerView = view.findViewById(R.id.cycle_recycler_view);

        }

        public TextView getTextView(){
            return this.textView;
        }

        public RecyclerView getRecyclerView(){ return this.recyclerView;}

    }


}
