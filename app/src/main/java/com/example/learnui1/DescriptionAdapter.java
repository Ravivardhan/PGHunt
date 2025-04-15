package com.example.learnui1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class DescriptionAdapter extends RecyclerView.Adapter<Description_Holder> {
    Context context;
    ArrayList<Description_class> list;

    public DescriptionAdapter(Context context, ArrayList<Description_class> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Description_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Description_Holder(LayoutInflater.from(context).inflate(R.layout.pg_description_card_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Description_Holder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(list.get(position).getDescription_image()).apply(new RequestOptions().placeholder(com.denzcoskun.imageslider.R.drawable.default_loading).error(com.denzcoskun.imageslider.R.drawable.default_error)).into(holder.description_image);
        //holder.description_image.setImageResource(list.get(position).getDescription_image());
        holder.description_sharing_count.setText(list.get(position).getSharing_count());
        holder.description_price.setText(list.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
