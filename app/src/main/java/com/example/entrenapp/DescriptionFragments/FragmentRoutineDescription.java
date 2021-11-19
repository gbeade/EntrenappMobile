package com.example.entrenapp.DescriptionFragments;




import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.entrenapp.App;
import com.example.entrenapp.R;
import com.example.entrenapp.api.model.User;
import com.example.entrenapp.apiClasses.Cycle;
import com.example.entrenapp.apiClasses.Routine;
import com.example.entrenapp.databinding.FragmentRoutineDescriptionBinding;


import com.example.entrenapp.executeRoutineActivity.ExecuteRoutineActivity;
import com.example.entrenapp.recyclerView.CycleAdapter;
import com.example.entrenapp.repository.NotificationHandler;
import com.example.entrenapp.repository.ReminderBroadcast;
import com.example.entrenapp.repository.Resource;
import com.example.entrenapp.repository.UserSession;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FragmentRoutineDescription extends Fragment {

    private Routine routine;
    private boolean favourite;
    private boolean isFavouritable;
    FragmentRoutineDescriptionBinding binding;
    private App app;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.routine = getArguments().getParcelable("Routine");
        this.favourite = getArguments().getBoolean("Favourite");
        this.isFavouritable = getArguments().getBoolean("IsFavouritable");


        app = (App) getActivity().getApplication();
        binding.routineTitle.setText(this.routine.getName());
        binding.duration.setText(getString(R.string.duraci_n)+ ": " + this.routine.getDuration() +" "+getString(R.string.minute));

        if (this.isFavouritable) {
            if (this.favourite) {
                binding.addFavourite.setVisibility(View.GONE);
                binding.removeFavourite.setVisibility(View.VISIBLE);
            } else {
                binding.addFavourite.setVisibility(View.VISIBLE);
                binding.removeFavourite.setVisibility(View.GONE);
            }


            binding.addFavourite.setOnClickListener(v -> app.getRoutineRepository().setFavourite(routine.getId()).observe(getViewLifecycleOwner(), voidResource -> {
                binding.addFavourite.setVisibility(View.GONE);
                binding.removeFavourite.setVisibility(View.VISIBLE);
                Snackbar.make(binding.btnTrain, R.string.agregadoFavoritos, BaseTransientBottomBar.LENGTH_SHORT).show();
                UserSession.setLastFavedRoutine(this.routine);
            }));

            binding.removeFavourite.setOnClickListener(v -> app.getRoutineRepository().deleteFavourite(routine.getId()).observe(getViewLifecycleOwner(), voidResource -> {
                binding.removeFavourite.setVisibility(View.GONE);
                binding.addFavourite.setVisibility(View.VISIBLE);
                Snackbar.make(binding.btnTrain, R.string.eliminadaFavoritos, BaseTransientBottomBar.LENGTH_SHORT).show();
                UserSession.setLastFavedRoutine(null);
            }));
        }else{
            binding.addFavourite.setVisibility(View.GONE);
            binding.removeFavourite.setVisibility(View.GONE);
        }

        fillRoutine();
        binding.btnTrain.setOnClickListener(v -> train());
        binding.trainLater.setOnClickListener(v -> schedulerPopup());

    }

    PopupWindow popupWindow;
    TimePicker timePicker;
    int daySwitch[] = {
            R.id.sunday_switch,
            R.id.monday_switch,
            R.id.tuesday_switch,
            R.id.wednesday_switch,
            R.id.thursday_switch,
            R.id.friday_switch,
            R.id.saturday_switch
    };

    View popupView;
    boolean isSomeoneOn = false;
    private void schedulerPopup() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_date_picker, null);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT; // (int)getContext().getResources().getDisplayMetrics().density * 400;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT; // (int)getContext().getResources().getDisplayMetrics().density * 300;
        popupWindow = new PopupWindow(popupView, width, height, true);
        Button btn = popupView.findViewById(R.id.btn_return);
        btn.setOnClickListener(view->createAlert());
        timePicker = (TimePicker)popupWindow.getContentView().findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.bodyContainer), Gravity.CENTER, 0, 0);

        for (int i=1; i<=7; i++)
            ((Switch)popupView.findViewById(daySwitch[i-1])).setOnClickListener((v)->
            {
                isSomeoneOn = false;
                for (int j=1; j<=7 && !isSomeoneOn; j++)
                    if (((Switch)popupView.findViewById(daySwitch[j-1])).isChecked())
                        isSomeoneOn = true;
                if (isSomeoneOn) btn.setText(getString(R.string.save));
                else btn.setText(getString(R.string.cancel));
            });
    }

    private void createAlert() {

        if (isSomeoneOn) {
            for (int day = 1; day <= 7; day++) {
                if (((Switch) popupView.findViewById(daySwitch[day - 1])).isChecked()) {
                    Intent intent = new Intent(getContext(), ReminderBroadcast.class);
                    intent.putExtra("routineName", routine.getName());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                    calendar.set(Calendar.SECOND, 0);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                }
            }
            Toast.makeText(getActivity(), getString(R.string.reminder_set), Toast.LENGTH_SHORT).show();
        }
        popupWindow.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoutineDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private void train(){
        Intent intent = new Intent(getActivity(), ExecuteRoutineActivity.class);
        intent.putExtra("Routine", this.routine);
        startActivity(intent);
    }

    private void fillRoutine(){
        FragmentRoutineViewModel viewModel = new ViewModelProvider(getActivity()).get(FragmentRoutineViewModel.class);
        viewModel.setRoutineId(this.routine);

        viewModel.getCycle().observe(getViewLifecycleOwner(), this::onChanged);
    }

    private void onChanged(List<Cycle> cycle) {

        for(Cycle c : cycle )
            if(!routine.getCycles().contains(c))
                routine.addCycle(c);

        RecyclerView.Adapter adapter = new CycleAdapter(routine.getCycles(), getActivity());
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.routineDescriptionCyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        else binding.routineDescriptionCyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.routineDescriptionCyclerView.setAdapter(adapter);
    }
}