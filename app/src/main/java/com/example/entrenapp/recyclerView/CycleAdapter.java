package com.example.entrenapp.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.ExerciseDescriptionActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Exercise;

import java.util.List;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.CycleVH> {
    private List<Cycle> cycles;
    private Context context;

    public CycleAdapter(List<Cycle> cycles , Context context){
        this.cycles=cycles;
        this.context=context;
    }


    @NonNull
    public CycleVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType ) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cycle_view_holder, viewGroup, false);

        return new CycleAdapter.CycleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleVH holder, int position) {
            holder.getTextView().setText(cycles.get(position).getName());
            RecyclerView.Adapter adapter = new CardAdapter(cycles.get(position).getExercises(), R.layout.summary_square_card, context, position1 -> {
                Intent intent = new Intent(context, ExerciseDescriptionActivity.class);
                intent.putExtra("Exercise",cycles.get(position).getExercises().get(position1));
                context.startActivity(intent);
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

            textView = (TextView) view.findViewById(R.id.cycle_text_view);
            recyclerView = view.findViewById(R.id.cycle_recycler_view);

        }

        public TextView getTextView(){
            return this.textView;
        }

        public RecyclerView getRecyclerView(){ return this.recyclerView;}

    }


}
