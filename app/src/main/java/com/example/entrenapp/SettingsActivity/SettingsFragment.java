package com.example.entrenapp.SettingsActivity;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.entrenapp.App;
import com.example.entrenapp.DescriptionFragments.DescriptionActivity;
import com.example.entrenapp.R;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentMyRoutinesBinding;
import com.example.entrenapp.databinding.FragmentSettingsBinding;
import com.example.entrenapp.recyclerView.CardAdapter;
import com.example.entrenapp.recyclerView.Cardable;
import com.example.entrenapp.repository.NotificationHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SettingsFragment extends Fragment {


    FragmentSettingsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.gotonots.setOnClickListener(v-> {
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationHandler.getNotificationChannel("ENTRENAPP").getId());
            startActivity(intent);
        });

        binding.lightMode.setOnClickListener( v -> {
            Resources.
        });


        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem settItem = menu.findItem(R.id.action_settings);
        settItem.setVisible(false);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        MenuItem sort = menu.findItem(R.id.action_sort);
        sort.setVisible(false);
        MenuItem filter = menu.findItem(R.id.action_filter);
        filter.setVisible(false);
    }


}
