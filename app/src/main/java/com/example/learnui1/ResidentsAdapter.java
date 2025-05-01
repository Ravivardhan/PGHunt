package com.example.learnui1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ResidentsAdapter extends RecyclerView.Adapter<ResidentsViewHolder> {
    Context context;
    ArrayList<Residents> list;

    public ResidentsAdapter(Context context, ArrayList<Residents> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ResidentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResidentsViewHolder(LayoutInflater.from(context).inflate(R.layout.resident_card,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ResidentsViewHolder holder, int position) {
            holder.username.setText(list.get(position).getUsername());
        Glide.with(holder.itemView.getContext()).load(list.get(position).getImageURL()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
