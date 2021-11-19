package com.example.entrenapp.bodyActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.entrenapp.App;
import com.example.entrenapp.BaseMenuActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.User;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityBodyBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;
import com.example.entrenapp.repository.Resource;
import com.example.entrenapp.repository.Status;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Comparator;

public class BodyActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private App app;
    private FilterViewModel filter;

    private Comparator<Routine> comparator;

    private static MenuItem id;
    public Comparator<Routine> getComparator() {
        return ( comparator == null ? Comparator.comparing(Routine::getName): comparator);
    }
    public void clearComparator() {
        comparator = Comparator.comparing(Routine::getName).reversed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();

        ActivityBodyBinding binding = ActivityBodyBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);


        filter = new ViewModelProvider(this).get(FilterViewModel.class);

        setContentView(root);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());


        //getUser();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                filter.addLetter(newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                searchItem.collapseActionView();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem settItem = menu.findItem(R.id.action_settings);
        settItem.setVisible(true);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareItem.setVisible(false);
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setVisible(false);

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
            return true;
        }else if (item.getItemId() == 16908332) {
            filter.clear();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            if(getIntent().getData() != null){
                Intent intent = new Intent(this,BodyActivity.class);
                startActivity(intent);
                finish();
            }
            boolean empty = !navController.popBackStack();
            if(empty){
                finish();
            }
            return true;
        }else if (item.getItemId() == R.id.action_sort) {
            sortPopup();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            navController.navigate(R.id.settingsFragment);
            return true;
        } else  {
            return super.onOptionsItemSelected(item);
        }
    }

    PopupWindow popupWindow;
    public void sortPopup() {

        View popupView;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_sort, null);
        int width = (int)getResources().getDisplayMetrics().density * 300;
        int height = (int)getResources().getDisplayMetrics().density * 550;
        popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(findViewById(R.id.bodyContainer), Gravity.CENTER, 0, 0);

        Button btn_difficulty = popupView.findViewById(R.id.btn_difficulty);
        btn_difficulty.setOnClickListener( v->setComparatorAndClosePopup(Comparator.comparing(Routine::getDifficulty)));

        Button btn_name = popupView.findViewById(R.id.btn_name);
        btn_name.setOnClickListener( v->setComparatorAndClosePopup(Comparator.comparing(Routine::getName)));

        Button btn_duration = popupView.findViewById(R.id.btn_duration);
        btn_duration.setOnClickListener( v->setComparatorAndClosePopup(Comparator.comparing(Routine::getDuration)));

        Button btn_none = popupView.findViewById(R.id.btn_none);
        btn_none.setOnClickListener( v->setComparatorAndClosePopup(Comparator.comparing(Routine::getName).reversed()));
    }

    private void setComparatorAndClosePopup(Comparator<Routine> c) {
        comparator = c;
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId()).getItemId());
        popupWindow.dismiss();
        Toast.makeText(this, getString(R.string.sort_ok), Toast.LENGTH_SHORT).show();
    }

}