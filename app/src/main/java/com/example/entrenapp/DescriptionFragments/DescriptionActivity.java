package com.example.entrenapp.DescriptionFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.entrenapp.BaseMenuActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.ActivityDescriptionBinding;
import com.example.entrenapp.databinding.ToolbarMainBinding;

import java.util.StringTokenizer;

public class DescriptionActivity extends BaseMenuActivity {
    private Routine routine;
    NavController navController;
    private boolean favourite;
    private boolean isFavouritable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDescriptionBinding binding = ActivityDescriptionBinding.inflate(getLayoutInflater());

        int id;
        if(getIntent().getData() == null) {
            this.routine = getIntent().getParcelableExtra("Routine");
            this.favourite = getIntent().getBooleanExtra("Favourite", false);
            this.isFavouritable = getIntent().getBooleanExtra("IsFavouritable", true);
        }
        else {
            StringTokenizer tokenizer = new StringTokenizer(getIntent().getData().toString(), "=");
            String auxId = tokenizer.nextToken();
            auxId = tokenizer.nextToken();
            id = Integer.parseInt(auxId);
        }

        View root = binding.getRoot();

        ToolbarMainBinding binding2 = ToolbarMainBinding.bind(root);
        setContentView(root);

        binding2.toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(binding2.toolbar);

        Bundle bundle = new Bundle();
        bundle.putParcelable("Routine", this.routine);
        bundle.putBoolean("Favourite",this.favourite);
        bundle.putBoolean("IsFavouritable",this.isFavouritable);

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
        }else if(item.getItemId() == R.id.action_share){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = R.string.baseUrl + "/routine?id=" + Integer.toString(this.routine.getId());
            intent.setData(Uri.parse(url));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent shareIntent = Intent.createChooser(intent, null);
            startActivity(shareIntent);
            return true;
        }else
            return super.onOptionsItemSelected(item);

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
                Log.d("AppBar", "onQueryTextChange -> " + newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                Log.d("AppBar", "onQueryTextSubmit -> " + query);
                searchItem.collapseActionView();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.action_search);
        shareItem.setVisible(false);
        MenuItem settingsItem = menu.findItem(R.id.action_filter);
        settingsItem.setVisible(false);
        MenuItem back = menu.findItem(R.id.action_settings);
        back.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

}