package com.example.quickcash.View;

<<<<<<< HEAD
import android.content.SharedPreferences;
=======
import android.Manifest;
import android.content.pm.PackageManager;
>>>>>>> 2bcefbe (added mapview into dashboard fragment but map doesnt display)
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
=======
import androidx.core.app.ActivityCompat;
>>>>>>> 2bcefbe (added mapview into dashboard fragment but map doesnt display)
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quickcash.Model.Task;
import com.example.quickcash.R;
import com.example.quickcash.AddTaskViewModel;
import com.example.quickcash.Util.Constants;
import com.example.quickcash.Util.TaskAdapter;
import com.example.quickcash.databinding.FragmentDashboardBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DashboardFragment extends Fragment implements OnMapReadyCallback{

        AddTaskViewModel viewModel;
        private RecyclerView taskListRecyclerView;
        TaskAdapter taskAdapter;
        FirebaseRecyclerOptions<Task> options;
        FragmentDashboardBinding binding;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        private MapView dashMap;
        public DashboardFragment() {
            // Required empty public constructor
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());
        editor = sharedPreferences.edit();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            inflater.inflate(R.layout.fragment_dashboard, container, false);

            viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
            binding =  FragmentDashboardBinding.inflate(inflater, container, false);

            binding.setViewModel(viewModel);
            dashMap = view.findViewById(R.id.dashMapView);
            Bundle mapViewBundle = null;
            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(Constants.MAPVIEW_BUNDLE_KEY);
            }

            dashMap.onCreate(mapViewBundle);

            dashMap.getMapAsync((OnMapReadyCallback) this);
            return binding.getRoot();
        }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            //Setting up recyclerview
            Query query = FirebaseDatabase.getInstance().
                    getReference().child("TASKS");
            //Getting the query from Firebase
            options = new FirebaseRecyclerOptions.Builder<Task>().setLifecycleOwner(getViewLifecycleOwner()).setQuery(query, Task.class).build();
            //Instaniating the adapter
            taskAdapter = new TaskAdapter(options, getActivity().getApplicationContext(), Navigation.findNavController(view));
            //Finding the recyclerview
            taskListRecyclerView = getView().findViewById(R.id.taskListRecyclerView);
            //Setting the layout of the recyclerview to Linear
            taskListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,true));
            taskListRecyclerView.setHasFixedSize(true);
            //Adding the adapter to the recyclerview
            taskListRecyclerView.setAdapter(taskAdapter);


            dashMap.onResume();


            //Navigation to Add Tasks Page
            NavDirections actionDashboardToCreateTasks = DashboardFragmentDirections.dashboardToCreateTask();
            FloatingActionButton goToAddTasksButton = (FloatingActionButton) getView().findViewById(R.id.goToAddTaskButton);

            goToAddTasksButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(view).navigate(actionDashboardToCreateTasks);
                }
            });

            //Logout and navigate to login page
            NavDirections actionDashboardToLogin = DashboardFragmentDirections.dashboardToLogin();
            FloatingActionButton logOutButton = (FloatingActionButton) getView().findViewById(R.id.logOutButton);

            logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean("LOGGED_IN", false);
                    editor.apply();
                    Navigation.findNavController(view).navigate(actionDashboardToLogin);
                }
            });
        }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(Constants.MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(Constants.MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        dashMap.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onStart() {
        super.onStart();
        dashMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        dashMap.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        dashMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        dashMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dashMap.onLowMemory();
    }
}



