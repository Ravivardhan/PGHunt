package com.example.learnui1.ui.home;

import android.Manifest;
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
import com.example.learnui1.display_pg_details_class;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FusedLocationProviderClient fusedLocationClient;
    Double lat = 12.9716; // Default to Bangalore coordinates
    Double lng = 77.5946;
    ArrayList<Category> category_list=new ArrayList<>();
    ArrayList<display_pg_details_class> pg_with_category_list =new ArrayList<>();
    RecyclerView category_recyclerView,main_recycler_view;
    ImageSlider imageSlider;
    Main_adapter main_adapter;
    TextView sortBy;
    ArrayList<main_pg_class> list=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        get_current_coordinates();
        fetch_pg_list_with_category();
        sortBy=binding.sortBy;
        //recycler view initialization
        category_recyclerView=binding.categoryRecyclerView;
        //image slider initialization and functionality
        imageSlider=binding.imageSlider;
        ArrayList<SlideModel> images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.pg1,"Sri sai Mens Pg", ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pg2,"PG Name 2",ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pg3,"PG Name 3",ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pg4,"PG Name 4",ScaleTypes.FIT));

        imageSlider.setImageList(images);
        category_list.clear();
        ///image slider done
        category_list.add(new Category("Womens PG",R.drawable.women));
        category_list.add(new Category("Mens PG",R.drawable.man));
        category_list.add(new Category("Working women pg",R.drawable.working_women));
        category_list.add(new Category("OYO",R.drawable.pg_test_image));



        CategoryRecyclerAdapter categoryRecyclerAdapter=new CategoryRecyclerAdapter(getContext(),category_list);
        LinearLayoutManager category_layout_manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        category_recyclerView.setLayoutManager(category_layout_manager);
        category_recyclerView.setAdapter(categoryRecyclerAdapter);


        // MAIN RECYCLER VIEW IMPLEMENTATION
        main_recycler_view=binding.myRecycler;
        get_current_coordinates();
        main_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));


        //  F I R E B A S E   C O M I N G  I N       G A  M E



        //sorting logic
        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View view=LayoutInflater.from(getContext()).inflate(R.layout.sorting_options,null);
                view.setBackgroundResource(R.drawable.top_rounded_bg);
                BottomSheetDialog dialog=new BottomSheetDialog(getContext());
                dialog.setContentView(view);
                dialog.show();
                Button apply_sort=view.findViewById(R.id.btnApply) ;
                apply_sort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup selected_sortBy=view.findViewById(R.id.radioGroupSort);
                        RadioGroup selected_order=view.findViewById(R.id.radioGroupOrder);

                        RadioButton r_btn=view.findViewById(selected_sortBy.getCheckedRadioButtonId());
                        String selected_sort_text=r_btn.getText().toString();
                        RadioButton o_btn=view.findViewById(selected_order.getCheckedRadioButtonId());
                        String selected_sort_order_text=o_btn.getText().toString();

                        sort_items(selected_sort_text,selected_sort_order_text);
                        dialog.dismiss();
                    }
                });



            }
        });

        return root;
    }
public void sort_items(String selected_sort_by,String selected_sort_order_text)
{
    //ArrayList<Integer> price_list=new ArrayList<Integer>();
    //list.forEach(item->price_list.add(Integer.parseInt(item.starting_price)));
    switch (selected_sort_by)
    {
        case "Name":list.sort(Comparator.comparing(item->item.main_name.toLowerCase().split(" ")[0]));
        break;
        case "Price":list.sort(Comparator.comparing(item->Integer.parseInt(item.starting_price)));
        break;
        case "Rating":list.sort(Comparator.comparing(item->item.rating));
        break;
        case "Location":list.sort(Comparator.comparing(item->item.distance_in_km));
        break;
    }
    if(selected_sort_order_text.equals("Descending"))
    {
        Collections.reverse(list);
    }
    main_adapter.updateList(list);
    main_adapter.notifyDataSetChanged();
    //Toast.makeText(getContext(), list.get(0).p, Toast.LENGTH_SHORT).show();

    //Log.d("list prices",price_list.toString());
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void get_current_coordinates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                            // Update adapter if it exists
                            main_adapter = new Main_adapter(getContext(), list, lat, lng);
                            main_recycler_view.setAdapter(main_adapter);
                            fetchFirebaseData();
                            if (main_adapter != null) {
                                main_adapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            // Request permission if not granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    public void fetchFirebaseData()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PG");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    main_pg_class mainPgClass=dataSnapshot.getValue(main_pg_class.class);
                    list.add(mainPgClass);
                }
                main_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void fetch_pg_list_with_category()
    {
        pg_with_category_list.clear();
        DatabaseReference pg_list_with_cat=FirebaseDatabase.getInstance().getReference("PG");
        pg_list_with_cat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    display_pg_details_class pg_details_class=dataSnapshot.getValue(display_pg_details_class.class);
                    pg_with_category_list.add(pg_details_class);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            get_current_coordinates(); // Try again if permission granted
        }
    }
}