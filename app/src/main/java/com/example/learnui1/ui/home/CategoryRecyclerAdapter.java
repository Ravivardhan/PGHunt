package com.example.learnui1.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.learnui1.R;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    ArrayList<Category> categories=new ArrayList<>();

    public CategoryRecyclerAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Glide.with(context)
                .load(categories.get(position).getCategory_image())
                .apply(RequestOptions.circleCropTransform())

                .into(holder.category_image);
        holder.category_text.setText(categories.get(position).getCategory_name());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
