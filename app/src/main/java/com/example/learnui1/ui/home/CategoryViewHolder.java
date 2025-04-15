package com.example.learnui1.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnui1.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    ImageView category_image;
    TextView category_text;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        category_image=itemView.findViewById(R.id.category_image);
        category_text=itemView.findViewById(R.id.category_text);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Context context=itemView.getContext();
//                Activity activity=(Activity) context;
//                Intent i=new Intent(activity,Category_activity.class);
//                i.putExtra("category_type",category_text.getText().toString());
//                activity.startActivity(i);
//
//            }
//        });



    }
}
