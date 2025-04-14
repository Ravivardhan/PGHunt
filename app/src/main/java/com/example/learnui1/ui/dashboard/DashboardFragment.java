package com.example.learnui1.ui.dashboard;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.learnui1.DistanceCalculator;
import com.example.learnui1.databinding.FragmentDashboardBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.Manifest;
import android.location.Location;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DashboardFragment extends Fragment {
    EditText longitude,latitude;
    Double lat,lng;
    Button find_btn;
    DistanceCalculator distanceCalculator;
    Double current_longitude,current_latitude;
    FusedLocationProviderClient fusedLocationClient;
    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Check location permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Ask permission if not granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Get last known location
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    });
        }
        find_btn=binding.findBtn;
        longitude=binding.longitude;
        latitude=binding.latitute;

        find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double get_latitude=Double.parseDouble(latitude.getText().toString());
                Double get_longitude=Double.parseDouble(longitude.getText().toString());
                if((get_longitude !=null && get_latitude != null)||(get_longitude!=0 && get_latitude!=0))
                {
                    Double distance=DistanceCalculator.getDistanceInKm(lat,lng,get_latitude,get_longitude);
                    Toast.makeText(requireContext(), String.format("%.2f KM",distance), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
