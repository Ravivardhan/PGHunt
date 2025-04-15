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

import com.example.learnui1.Description_class;
import com.example.learnui1.DistanceCalculator;
import com.example.learnui1.PushModel;
import com.example.learnui1.R;
import com.example.learnui1.databinding.FragmentDashboardBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.Manifest;
import android.location.Location;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    EditText longitude,latitude;
    Double lat,lng;
    Button find_btn;
    DistanceCalculator distanceCalculator;
    Double current_longitude,current_latitude;
    FusedLocationProviderClient fusedLocationClient;
    private FragmentDashboardBinding binding;
    EditText  address,category,coordinates,location,main_image, main_name,owner_name,pg_name,rating,rules,starting_price;
    public Description_class description=new Description_class("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/India_andhra-pradesh_hyderabad_hitec-city.jpg/250px-India_andhra-pradesh_hyderabad_hitec-city.jpg","2","6000");
    ArrayList<Description_class> desc_list=new ArrayList<>();
    Button push_btn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        load_Edit_Views();
        push_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc_list.add(description);
                desc_list.add(description);
                PushModel pushModel=new PushModel(address.getText().toString(),category.getText().toString()
                ,coordinates.getText().toString(),location.getText().toString(),main_image.getText().toString()
                ,main_name.getText().toString(),owner_name.getText().toString(),pg_name.getText().toString()
                ,rating.getText().toString(),rules.getText().toString(),starting_price.getText().toString()
                ,desc_list);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                DatabaseReference db=databaseReference.child("PG").push();

                db.setValue(pushModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                databaseReference.push();
            }
        });

        return root;
    }

    public void load_Edit_Views()
    {
        address=binding.pushAddress;
        category=binding.pushCategory;
        coordinates=binding.pushCoordinates;
        location=binding.pushLocation;
        main_image=binding.pushMainImage;
        main_name=binding.pushMainName;
        owner_name=binding.pushOwnerName;
        pg_name=binding.pushPgName;
        rating=binding.pushRating;
        rules=binding.pushRules;
        starting_price=binding.pushStartingPrice;
        push_btn=binding.pushBtn;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
