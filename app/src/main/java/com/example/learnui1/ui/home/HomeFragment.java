package com.example.learnui1.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.learnui1.R;
import com.example.learnui1.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment implements CategoryRecyclerAdapter.OnCategoryClickListener {

    private FragmentHomeBinding binding;
    ArrayList<main_pg_class> sel_category_list=new ArrayList<>();

    private FusedLocationProviderClient fusedLocationClient;
    private Double lat = 12.9716; // Default to Bangalore coordinates
    private Double lng = 77.5946;
    private ArrayList<Category> category_list = new ArrayList<>();
    private RecyclerView category_recyclerView, main_recycler_view;
    private ImageSlider imageSlider;
    private Main_adapter main_adapter;
    private TextView sortBy;
    private SharedPreferences sharedPreferences;
    private ArrayList<main_pg_class> list = new ArrayList<>();
    private ArrayList<main_pg_class> filteredList = new ArrayList<>();
    FirebaseAuth mauth;
    FirebaseUser muser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        new ViewModelProvider(this).get(HomeViewModel.class);


        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("selected_cat", MODE_PRIVATE);
        String currentCat = sharedPreferences.getString("cat", "");
        Log.d("Current Category", currentCat);

        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
        if (muser==null)
        {
            Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_LONG).show();
        }
        // Initialize UI components
        initViews();
        setupImageSlider();
        setupCategories();

        setupRecyclerViews();
        setupSorting();

        // Fetch data
        get_current_coordinates();

        return root;
    }

    private void initViews() {
        sortBy = binding.sortBy;
        category_recyclerView = binding.categoryRecyclerView;
        imageSlider = binding.imageSlider;
        main_recycler_view = binding.myRecycler;
    }

    private void setupImageSlider() {
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.pg1, "Sri sai Mens Pg", ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pg2, "PG Name 2", ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pg3, "PG Name 3", ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pg4, "PG Name 4", ScaleTypes.FIT));
        imageSlider.setImageList(images);
    }

    private void setupCategories() {
        category_list.clear();
        //category_list.add(new Category("All", R.drawable.pg_test_image));
        category_list.add(new Category("Womens PG", R.drawable.women));
        category_list.add(new Category("Mens PG", R.drawable.man));
        category_list.add(new Category("Working women pg", R.drawable.working_women));
        category_list.add(new Category("OYO", R.drawable.pg_test_image));

        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(
                requireContext(),
                category_list,
                this
        );
        category_recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        category_recyclerView.setAdapter(categoryRecyclerAdapter);
    }

    private void setupRecyclerViews() {
        main_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        main_adapter = new Main_adapter(getContext(), list, lat, lng);
        main_recycler_view.setAdapter(main_adapter);
    }

    private void setupSorting() {
        sortBy.setOnClickListener(v -> showSortDialog());
    }

    private void showSortDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.sorting_options, null);
        view.setBackgroundResource(R.drawable.top_rounded_bg);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view);
        dialog.show();

        Button apply_sort = view.findViewById(R.id.btnApply);
        apply_sort.setOnClickListener(v -> {
            RadioGroup selected_sortBy = view.findViewById(R.id.radioGroupSort);
            RadioGroup selected_order = view.findViewById(R.id.radioGroupOrder);

            RadioButton r_btn = view.findViewById(selected_sortBy.getCheckedRadioButtonId());
            String selected_sort_text = r_btn.getText().toString();
            RadioButton o_btn = view.findViewById(selected_order.getCheckedRadioButtonId());
            String selected_sort_order_text = o_btn.getText().toString();

            sort_items(selected_sort_text, selected_sort_order_text);
            dialog.dismiss();
        });
    }

    public void sort_items(String selected_sort_by, String selected_sort_order_text) {
        ArrayList<main_pg_class> listToSort;
        if(!sel_category_list.isEmpty()) {
             listToSort = new ArrayList<>(sel_category_list);
        }
        else {
            listToSort=new ArrayList<>(list);
        }

        switch (selected_sort_by) {
            case "Name":
                listToSort.sort(Comparator.comparing(item -> item.main_name.toLowerCase().split(" ")[0]));
                break;
            case "Price":
                listToSort.sort(Comparator.comparing(item -> Integer.parseInt(item.starting_price)));
                break;
            case "Rating":
                listToSort.sort(Comparator.comparing(item -> item.rating));
                break;
            case "Location":
                listToSort.sort(Comparator.comparing(item -> item.distance_in_km));
                break;
        }

        if (selected_sort_order_text.equals("Descending")) {
            Collections.reverse(listToSort);
        }

        list.clear();
        list.addAll(listToSort);
        main_adapter.updateList(list);
        main_adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void get_current_coordinates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        // Got last known location
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        Log.d("Location", "Got location: " + lat + ", " + lng);

                        // Initialize adapter with location
                        main_adapter = new Main_adapter(getContext(), list, lat, lng);
                        main_recycler_view.setAdapter(main_adapter);

                        // Fetch data after getting location


                        fetchFirebaseData();
                    } else {
                        Log.e("Location", "Location is null");
                        // Use default location if couldn't get current location
                        lat = 12.9716;
                        lng = 77.5946;
                        fetchFirebaseData();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Location", "Error getting location", e);
                    // Use default location if failed
                    lat = 12.9716;
                    lng = 77.5946;
                    fetchFirebaseData();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, try to get location again
                get_current_coordinates();
            } else {
                // Permission denied, use default location
                Log.d("Location", "Location permission denied");
                lat = 12.9716;
                lng = 77.5946;
                fetchFirebaseData();
            }
        }
    }

    public void fetchFirebaseData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    main_pg_class mainPgClass = dataSnapshot.getValue(main_pg_class.class);
                    if (mainPgClass != null) {
                        list.add(mainPgClass);
                    }
                }
                // Initially show all items
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error loading data", error.toException());
            }
        });
    }

    @Override
    public void onCategoryClick(String categoryName) {
        sharedPreferences.edit()
                .putString("cat", categoryName)
                .apply();
        Log.d("selected category",categoryName);
        sel_category_list.clear();
        if(categoryName!="" || categoryName != null)
        {
            for(main_pg_class obj:list)
            {
                if(obj.getCategory().equals(categoryName))
                {
                    sel_category_list.add(obj);
                }
                else {
                    Log.d("not matched :",obj.getCategory());
                }
            }
        }
        main_adapter.notifyDataSetChanged();
        main_adapter.updateList(sel_category_list);
    }




}