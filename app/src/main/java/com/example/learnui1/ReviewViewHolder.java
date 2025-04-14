package com.example.learnui1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    ShapeableImageView review_image;
    TextView review_name,review_time,review_text;
    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        review_image=itemView.findViewById(R.id.review_image);
        review_name=itemView.findViewById(R.id.review_name);
        review_time=itemView.findViewById(R.id.review_time);
        review_text=itemView.findViewById(R.id.review_text);

    }
}
