package com.example.entrenapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;


import java.util.ArrayList;


public class YourRoutinesActivity extends BaseMenuActivity {

    private ArrayList<String> routines= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityYourRoutinesBinding binding = ActivityYourRoutinesBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);



        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        fillRoutines();

        RecyclerView.Adapter adapter = new RoutineAdapter(this.routines);
        binding.routineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.routineRecyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareItem.setVisible(false);
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }


    private void fillRoutines(){
       for(int i = 0 ; i < 50 ; i++)
            routines.add("Pecho plano");
    }


}
