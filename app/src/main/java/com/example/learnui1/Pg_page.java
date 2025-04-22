package com.example.learnui1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.learnui1.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class Pg_page extends AppCompatActivity {
    TextView display_pg_name,display_owner_name,display_rules,display_address,display_category;
    RecyclerView description_recycler;
    ImageView back_btn;
    Button navigation_button,call_button;
    String coordinates,lat,lng,mobile;
    String unique;
    ArrayList<display_pg_details_class> display_pg_details=new ArrayList<>();
    ArrayList<Description_class> list=new ArrayList<>();
    ArrayList<SlideModel> slide_model_list=new ArrayList<>();

    ArrayList<Review> reviews=new ArrayList<>();


    String pg_name,owner_name,address,category,rules;
    DatabaseReference databaseReference;
    DatabaseReference reviewDataReference;
    StorageReference storageReference;

    String intent_pg_name;
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pg_page);
        back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->go_back());

        //initialize textViews
        display_pg_name=findViewById(R.id.display_pg_name);
        display_owner_name=findViewById(R.id.display_owner_name);
        display_address=findViewById(R.id.display_address);
        display_category=findViewById(R.id.display_category);
        display_rules=findViewById(R.id.display_rules);
        navigation_button=findViewById(R.id.navigation_button);
        call_button=findViewById(R.id.call_button);

        //setting textviiews
        intent_pg_name=getIntent().getStringExtra("pg_name");

        //set_pg_details(display_pg_details.get(index));
        findPgByName(intent_pg_name);

    navigation_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String uri="geo:"+lat+","+lng+"?q="+lat+","+lng;
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
        else
        {
            //Toast.makeText(Pg_page.this, "gmaps not installed", Toast.LENGTH_SHORT).show();
            Intent fallbackIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng));
            startActivity(fallbackIntent);
        }
        }
    });

    call_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String uri="tel:"+mobile;
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse(uri));
            startActivity(intent);
        }
    });



    }

    public void findPgByName(String pgName) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PG");
        ref.orderByChild("main_name").equalTo(pgName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d("FIREBASE_SEARCH", "Found " + dataSnapshot.getChildrenCount() + " PGs with name: " + pgName);

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Get the unique ID (key)
                                String uniqueId = snapshot.getKey();
                                Log.d("FIREBASE_SEARCH", "PG ID: " + uniqueId);
                                unique=uniqueId;
                                on_complete();
                                // Log all available data
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    Log.d("FIREBASE_SEARCH", child.getKey() + ": " + child.getValue());
                                }



                            }
                        } else {
                            Log.d("FIREBASE_SEARCH", "No PG found with name: " + pgName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("FIREBASE_SEARCH", "Error searching for PG: " + databaseError.getMessage());
                    }
                });
    }
    public void on_complete()
    {
        load_sharing_recycler_view();

        // f i r e   b  a  s e
        databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+unique);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String db_pg_name=snapshot.child("pg_name").getValue(String.class);
                    String db_owner_name=snapshot.child("owner_name").getValue(String.class);
                    String db_address=snapshot.child("address").getValue(String.class);
                    String rules=snapshot.child("rules").getValue(String.class);
                    String db_category=snapshot.child("category").getValue(String.class);
                    coordinates=snapshot.child("coordinates").getValue(String.class);
                    set_text_views(db_pg_name,db_owner_name,db_address,db_category,rules);
                    mobile=snapshot.child("mobile").getValue(String.class);
                    lat=coordinates.split(":")[0];
                    lng=coordinates.split(":")[1];

                }
            }

            // storage reference


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // P G  PAGE  IMAGE SLIDER (above reviews )...
        ImageSlider slider=findViewById(R.id.pg_page_image_slider);
//        load_sliding_images();
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("PG/"+unique);
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for(StorageReference item:listResult.getItems())
            {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    String url=uri.toString();
                    slide_model_list.add(new SlideModel(url,ScaleTypes.CENTER_CROP));

                    if(slide_model_list.size()==listResult.getItems().size())
                    {
                        slider.setImageList(slide_model_list);

                    }
                });
            }
        });

        reviewDataReference=FirebaseDatabase.getInstance().getReference("PG/"+unique+"/reviews");
        reviewDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        Review r=dataSnapshot.getValue(Review.class);
                        reviews.add(r);
                    }
                    load_reviews_recycler_view();
                }
                else {
                    Toast.makeText(Pg_page.this, "No Reviews available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //     M E T H O D S    T O    L O A D    T H E   D A T A & S E T T I N G  V I E W S....
    public void go_back()
    {
        Intent i=new Intent(Pg_page.this, MainActivity.class);
        startActivity(i);
    }

    public void load_reviews_recycler_view()
    {
        //review recycler view

        RecyclerView review_recycler_view=findViewById(R.id.reviews_recycler_view);
        review_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter reviewAdapter=new ReviewAdapter(this,reviews);
        review_recycler_view.setAdapter(reviewAdapter);
    }

    public void load_sharing_recycler_view() {
        // Initialize RecyclerView and adapter first
        description_recycler = findViewById(R.id.descriptionRecycler);
        description_recycler.setLayoutManager(new LinearLayoutManager(this));

        // Initialize empty list and adapter
        list = new ArrayList<>();
        DescriptionAdapter descriptionAdapter = new DescriptionAdapter(this, list);
        description_recycler.setAdapter(descriptionAdapter);

        // Load data from Firebase
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("PG/"+unique+"/description");
        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear existing data

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Description_class dc = dataSnapshot.getValue(Description_class.class);
                    if (dc != null) {
                        list.add(dc);
                        Log.d("FirebaseData", "Added description: " + dc.getDescription_image());
                    }
                }

                // Notify adapter that data changed
                descriptionAdapter.notifyDataSetChanged();

                // Log all loaded items
                for(Description_class d : list) {
                    Log.d("LoadedData", "Price: " + d.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load descriptions", error.toException());
                Toast.makeText(Pg_page.this, "Failed to load room options", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void set_text_views(String pg_name,String owner_name,String address,String category,String rules)
    {
        // --> setting text views
        display_pg_name.setText(pg_name);
        display_owner_name.setText(owner_name);
        display_address.setText(address);
        display_category.setText(category);
        display_rules.setText(rules);
    }
    public void set_pg_details(display_pg_details_class obj)
    {
        pg_name=obj.getPg_name();
        address=obj.getAddress();
        owner_name=obj.getOwner_name();
        category=obj.getCategory();
        rules=obj.getRules();
    }



//    public void load_sliding_images()
//    {
//        slide_model_list.clear();
//        slide_model_list.add(new SlideModel(R.drawable.p1,ScaleTypes.FIT));
//        slide_model_list.add(new SlideModel(R.drawable.p2,ScaleTypes.FIT));
//        slide_model_list.add(new SlideModel(R.drawable.p3,ScaleTypes.FIT));
//        slide_model_list.add(new SlideModel(R.drawable.p4,ScaleTypes.FIT));
//
//    }

}