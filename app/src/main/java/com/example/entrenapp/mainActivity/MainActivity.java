package com.example.entrenapp.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entrenapp.App;
import com.example.entrenapp.api.model.Credentials;
import com.example.entrenapp.bodyActivity.BodyActivity;
import com.example.entrenapp.databinding.ActivityMainBinding;
import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.repository.Status;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText username;
    private EditText password;
    private App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //si tenemos el token , go to next


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            login();
        });

        username = binding.editTextTextPersonName;
        password = binding.editTextTextPassword;
        app = ((App)getApplication());


    }

    public void login() {


        Credentials userData = new Credentials(username.getText().toString(),password.getText().toString());

        app.getUserRepository().login(userData).observe(this,r->{

            if (r.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(r.getData().getToken());
                Intent intent = new Intent(this, BodyActivity.class);
                startActivity(intent);

            } else {
                Log.d("Error","Usuario incorrect");

            }


        });


    }



}