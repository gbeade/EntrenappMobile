package com.example.entrenapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class YourRoutinesActivity extends AppCompatActivity {

    private ArrayList<Routine> routines= new ArrayList<>();
    private Predicate<Routine> filterFun = r -> true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityYourRoutinesBinding binding = ActivityYourRoutinesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fillRoutines();

      //   setFilter();
        RecyclerView.Adapter adapter = new CardAdapter(this.routines.stream().filter(filterFun).collect(Collectors.toList()), R.layout.extense_square_card, this);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.routineRecyclerView.setAdapter(adapter);

    }

    private void setFilter() {
        filterFun = r -> r.getCategory().equals("Atletico") && r.isEquipmentRequired();
    }

    private void fillRoutines(){
        routines.add(new Routine("Pecho Plano yyy", "Pecho", Routine.Difficulty.XTREME, false, new Date(), 4, 25));
        routines.add(new Routine("Pecho Plano xxx", "Brazos", Routine.Difficulty.XTREME, true, new Date(), 3, 56));
        routines.add(new Routine("Super atletico", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 5, 2));
        routines.add(new Routine("Anti grasa 2.0", "Atletico", Routine.Difficulty.XTREME, true, new Date(), 8, 25));
        routines.add(new Routine("Sprints 21", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 6, 40));
        routines.add(new Routine("Picar", "Atletico", Routine.Difficulty.XTREME, false, new Date(), 10, 25));

    }


}
