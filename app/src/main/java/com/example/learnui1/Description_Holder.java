package com.example.learnui1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Description_Holder extends RecyclerView.ViewHolder{
    ImageView description_image;
    TextView description_sharing_count,description_price;
    public Description_Holder(@NonNull View itemView) {
        super(itemView);
        description_image=itemView.findViewById(R.id.description_image);
        description_sharing_count=itemView.findViewById(R.id.description_sharing_count);
        description_price=itemView.findViewById(R.id.description_price);


    }
}
