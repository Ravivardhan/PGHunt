package com.example.learnui1;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResidentsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<Residents> list=new ArrayList<>();
    ResidentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_residents);


            load_residents();
            RecyclerView residents_recycler=findViewById(R.id.resident_recycler);
            residents_recycler.setLayoutManager(new LinearLayoutManager(this));
            adapter=new ResidentsAdapter(this,list);
            residents_recycler.setAdapter(adapter);






    }

    public void load_residents()
    {
        list.clear();
        String pg_id=getIntent().getStringExtra("pg_id");
        //firebase database
        databaseReference= FirebaseDatabase.getInstance().getReference("PG/"+pg_id+"/residents");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {

                    Residents resident=snapshot.getValue(Residents.class);
                    list.add(resident);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}