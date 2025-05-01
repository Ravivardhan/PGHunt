package com.example.learnui1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResidentsViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    ImageView profile;
    public ResidentsViewHolder(@NonNull View itemView) {
        super(itemView);
        username=itemView.findViewById(R.id.username);
        profile=itemView.findViewById(R.id.profile);
    }
}
