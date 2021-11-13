package com.example.entrenapp.DescriptionFragments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.entrenapp.BaseMenuActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityDescriptionBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;

public class DescriptionActivity extends BaseMenuActivity {
    private Routine routine;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDescriptionBinding binding = ActivityDescriptionBinding.inflate(getLayoutInflater());

        this.routine = getIntent().getParcelableExtra("Routine");

        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);


        Bundle bundle = new Bundle();
        bundle.putParcelable("Routine",this.routine);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.desc_nav_host_fragment);
         navController = navHostFragment.getNavController();
        navController.setGraph(R.navigation.desc_nav_graph,bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_back){
            boolean empty = !navController.popBackStack();
            if(empty){
                finish();
            }
            return true;
        }else
            return super.onOptionsItemSelected(item);

    }
}