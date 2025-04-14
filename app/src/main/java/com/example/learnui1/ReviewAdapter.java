package com.example.learnui1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    Context context;
    ArrayList<Review> list=new ArrayList<>();

    public ReviewAdapter(Context context, ArrayList<Review> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.review_card_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        //holder.review_image.setImageResource(list.get(position).getProfile_image());

        Glide.with(holder.itemView).load(list.get(position).getProfile_image()).apply(new
                RequestOptions().placeholder(com.denzcoskun.imageslider.R.drawable.default_loading).
                error(com.denzcoskun.imageslider.R.drawable.default_error)).into(holder.review_image);
        holder.review_name.setText(list.get(position).getUsername());
        holder.review_text.setText(list.get(position).getReview());
        //holder.review_time.setText(list.get(position).getReview_time());
        String review_time_ago=getTimeAgo(list.get(position).getReview_time());
        holder.review_time.setText(review_time_ago);

    }
    public static String getTimeAgo(String pastTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date past = sdf.parse(pastTimeStr);
            Date now = new Date();

            long seconds = (now.getTime() - past.getTime()) / 1000;

            if (seconds < 60) {
                return "just now";
            } else if (seconds < 3600) {
                return (seconds / 60) + " minutes ago";
            } else if (seconds < 86400) {
                return (seconds / 3600) + " hours ago";
            } else {
                return (seconds / 86400) + " days ago";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "unknown time";
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
