package com.example.entrenapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrenapp.databinding.ActivityFilterRoutineBinding;
import com.example.entrenapp.databinding.ActivityYourRoutinesBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;

public class FilterRoutineActivity extends BaseMenuActivity{
    private String TAG="CONSOLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityFilterRoutineBinding binding = ActivityFilterRoutineBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);



        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }else{
            Log.d(TAG,"chau");
        }
        Log.d(TAG,"hola");

    }




}
