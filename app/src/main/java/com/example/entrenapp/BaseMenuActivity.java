package com.example.entrenapp;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class BaseMenuActivity extends AppCompatActivity {

    private static final String TAG = "AppBar";

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
                Log.d(TAG, "onQueryTextChange -> " + newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit -> " + query);
                searchItem.collapseActionView();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // User chose the "Settings" item, show the app settings UI...
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            // User chose the "Share" action, share current item...
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message));
//            intent.setType("text/plain");
//
//            Intent shareIntent = Intent.createChooser(intent, null);
//            startActivity(shareIntent);
            return true;
        }else if(item.getItemId() == R.id.action_filter) {
            Intent intent = new Intent(this, FilterRoutineActivity.class);
            startActivity(intent);
            return true;
        }else {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);
        }
    }
}
