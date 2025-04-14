package com.example.learnui1.ui.home;

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


    }
}
