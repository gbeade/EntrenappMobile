package com.example.entrenapp.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.Credentials;
import com.example.entrenapp.api.model.User;
import com.example.entrenapp.bodyActivity.BodyActivity;
import com.example.entrenapp.databinding.ActivityMainBinding;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.repository.Resource;
import com.example.entrenapp.repository.Status;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText username;
    private EditText password;
    private App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        app = ((App)getApplication());
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


    }

    public void login() {

        Credentials userData = new Credentials(username.getText().toString(),password.getText().toString());

        app.getUserRepository().logout().observe(this, voidResource -> {
            Log.e("Logout","Hola");
            return;
        });


        app.getUserRepository().login(userData).observe(this,r->{

            if (r.getStatus() == Status.SUCCESS) {

                app.getPreferences().setAuthToken(r.getData().getToken());
                Intent intent = new Intent(this, BodyActivity.class);
                startActivity(intent);
            } else {
                ((TextView) this.findViewById(R.id.errorLogin)).setVisibility(View.VISIBLE);

            }


        });


    }





}