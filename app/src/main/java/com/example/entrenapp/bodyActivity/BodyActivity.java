package com.example.entrenapp.bodyActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.entrenapp.App;
import com.example.entrenapp.BaseMenuActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.databinding.ActivityBodyBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;
import com.example.entrenapp.repository.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BodyActivity extends BaseMenuActivity {
    BottomNavigationView bottomNavigationView;
    private App app;
    ViewModel filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBodyBinding binding = ActivityBodyBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);


        filter = new ViewModelProvider(this).get(FilterViewModel.class);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        app = (App) getApplication();
        getUser();

    }

    private void getUser(){
        app.getUserRepository().getCurrentUser().observe(this, userResource -> {
            if(userResource.getStatus()== Status.SUCCESS){
                app.getPreferences().setUserId(userResource.getData().getId());
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareItem.setVisible(false);
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setVisible(false);
        MenuItem back = menu.findItem(R.id.action_back);
        back.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
         NavController navController = navHostFragment.getNavController();
        if(item.getItemId()==R.id.action_filter){
            navController.navigate(R.id.filterRoutineFragment);
            return true;
        }else if(item.getItemId() == R.id.action_search) {
            Log.d("Searchimg", "searching");
            return true;
        }else if(item.getItemId() == R.id.action_back)   {
            navController.navigateUp();
            return true;
        }else
            return super.onOptionsItemSelected(item);

    }

}