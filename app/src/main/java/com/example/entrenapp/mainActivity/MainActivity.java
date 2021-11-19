package com.example.entrenapp.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.entrenapp.App;
import com.example.entrenapp.DescriptionFragments.DescriptionActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.Credentials;
import com.example.entrenapp.api.model.PagedList;
import com.example.entrenapp.api.model.User;
import com.example.entrenapp.bodyActivity.BodyActivity;
import com.example.entrenapp.databinding.ActivityMainBinding;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.repository.Resource;
import com.example.entrenapp.repository.Status;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText username;
    private EditText password;
    private App app;
    private boolean first = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        app = ((App)getApplication());

        first = getIntent().getBooleanExtra("first",true);


        if(app.getPreferences().getAuthToken() != null ){
            Intent intent = new Intent(this, BodyActivity.class);
            startActivity(intent);
        }


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLogin.setOnClickListener(view -> {
            login();
        });


        username = binding.editTextTextPersonName;
        password = binding.editTextTextPassword;

        // your text box
        EditText edit_txt = findViewById(R.id.editTextTextPassword);

        edit_txt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
               closeKeyboard();
            }
            return false;
        });
    }

    public void login() {
        String auxUsername = binding.editTextTextPersonName.getText().toString();

        Credentials userData = new Credentials(username.getText().toString(),password.getText().toString());


        app.getUserRepository().login(userData).observe(this,r->{

            if (r.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(r.getData().getToken());

                while(app.getPreferences().getAuthToken() == null) ;

                app.getUserRepository().getUsers().observe(this, result -> {
                    if(result != null && result.getData() != null) {
                        for (User user : result.getData().getContent()) {
                            if (user.getUsername().compareTo(auxUsername) == 0) {
                                app.getPreferences().setUserId(user.getId());
                                break;
                            }
                        }
                    }
                });

                Intent intent;
                //if(first)//{
                    intent = new Intent(this, BodyActivity.class);
//                }else{
//                    intent = new Intent(this, DescriptionActivity.class);
//                }
                    startActivity(intent);

                return;
            } else if(r.getStatus() == Status.ERROR){
                Snackbar snackbar = Snackbar.make(binding.btnLogin, R.string.errorLogin, BaseTransientBottomBar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();
                return;
            }else if (r.getStatus() != Status.LOADING){
                Snackbar snackbar = Snackbar.make(binding.btnLogin, R.string.unknown, BaseTransientBottomBar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackbar.show();
                return;
            }
        });


    }
    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }






}