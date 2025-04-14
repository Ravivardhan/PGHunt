package com.example.learnui1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
    ArrayList<display_pg_details_class> display_pg_details=new ArrayList<>();
    ArrayList<Description_class> list=new ArrayList<>();
    ArrayList<SlideModel> slide_model_list=new ArrayList<>();

    ArrayList<Review> reviews=new ArrayList<>();


    String pg_name,owner_name,address,category,rules;
    DatabaseReference databaseReference;
    DatabaseReference reviewDataReference;
    StorageReference storageReference;

    int index;
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

        //setting textviiews
        index=getIntent().getIntExtra("index",0);
        load_display_details();
        //set_pg_details(display_pg_details.get(index));






        load_sharing_recycler_view();

        // f i r e   b  a  s e
        databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+(index+1));


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
                    set_text_views(db_pg_name,db_owner_name,db_address,db_category,rules);
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
        storageReference=firebaseStorage.getReference("PG/"+(index+1));
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

        reviewDataReference=FirebaseDatabase.getInstance().getReference("PG/"+(index+1)+"/reviews");
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
    public void load_sharing_recycler_view()
    {
        //sharing recycler view
        load_sharing_details();
        DescriptionAdapter descriptionAdapter=new DescriptionAdapter(this,list);
        description_recycler=findViewById(R.id.descriptionRecycler);
        description_recycler.setLayoutManager(new LinearLayoutManager(this));
        description_recycler.setAdapter(descriptionAdapter);
    }
    public void load_reviews_recycler_view()
    {
        //review recycler view

        RecyclerView review_recycler_view=findViewById(R.id.reviews_recycler_view);
        review_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter reviewAdapter=new ReviewAdapter(this,reviews);
        review_recycler_view.setAdapter(reviewAdapter);
    }

    public void load_display_details()
    {
        display_pg_details.clear();
        display_pg_details.add(new display_pg_details_class("PG1","address1","owner1","category1","rules1"));
        display_pg_details.add(new display_pg_details_class("PG2","address2","owner2","category2","rules2"));
        display_pg_details.add(new display_pg_details_class("PG3","address3","owner3","category3","rules3"));
        display_pg_details.add(new display_pg_details_class("PG4","address4","owner4","category4","rules4"));

    }
    public void load_sharing_details()
    {
        list.clear();
        //add to list to view in recycler view
        list.add(new Description_class(R.drawable.pg1,"1 sharing","\u20B9 10,000"));
        list.add(new Description_class(R.drawable.pg1,"2 sharing","\u20B9 8,000"));
        list.add(new Description_class(R.drawable.pg1,"4 sharing","\u20B9 6,500"));

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