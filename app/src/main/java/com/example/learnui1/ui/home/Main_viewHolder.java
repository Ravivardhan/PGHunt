package com.example.learnui1.ui.home;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnui1.Pg_page;
import com.example.learnui1.R;

public class Main_viewHolder extends RecyclerView.ViewHolder
{
    ImageView main_image;
    TextView main_name,location,starting_price,main_rating_text,distance;
    //,distance;
    RatingBar ratingBar;


    public Main_viewHolder(@NonNull View itemView) {
        super(itemView);

        main_image=itemView.findViewById(R.id.main_image);
        main_name=itemView.findViewById(R.id.main_name);
        location=itemView.findViewById(R.id.main_location);
        starting_price=itemView.findViewById(R.id.main_starting_price);
        ratingBar=itemView.findViewById(R.id.ratingBar);
        distance=itemView.findViewById(R.id.distance);
        main_rating_text=itemView.findViewById(R.id.main_rating_text);
        //distance=itemView.findViewById(R.id.distance);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION)
                {

                    Context context=itemView.getContext();
                    Activity activity=(Activity) context;
                    Intent i=new Intent(activity,Pg_page.class);
                    i.putExtra("pg_name",main_name.getText().toString());

                    activity.startActivity(i);
                    activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left
                    );

                }
            }
        });

    }
}
