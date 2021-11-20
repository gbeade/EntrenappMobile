package com.example.entrenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.entrenapp.mainActivity.MainActivity;
import com.example.entrenapp.repository.UserSession;

public class PrivateActivity extends AppCompatActivity {

    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       checkToken();
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkToken();
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkToken();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkToken();
    }

    public void checkToken(){
        app = (App)getApplication();
        if(app.getPreferences().getAuthToken() == null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("first",false);
            startActivity(intent);
        }
    }

}
