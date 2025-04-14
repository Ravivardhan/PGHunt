package com.example.learnui1.ui.home;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.number.NumberFormatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.learnui1.DistanceCalculator;
import com.example.learnui1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main_adapter extends RecyclerView.Adapter<Main_viewHolder> {
    Context context;
    FusedLocationProviderClient fusedLocationClient;
    ArrayList<main_pg_class> list;
    Double lat,lng;

    public Main_adapter(Context context, ArrayList<main_pg_class> list,Double lat,Double lng) {
        this.context = context;
        this.list = list;
        this.lat=lat;
        this.lng=lng;
    }


    @NonNull
    @Override
    public Main_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Main_viewHolder(LayoutInflater.from(context).inflate(R.layout.main_card_view,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull Main_viewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(list.get(position).getMain_image()).apply(new RequestOptions().placeholder(com.denzcoskun.imageslider.R.drawable.default_loading)
                .error(com.denzcoskun.imageslider.R.drawable.default_error)).into(holder.main_image);
        //holder.main_image.setImageResource(list.get(position).getMain_image());
        holder.main_name.setText(list.get(position).getMain_name());
        holder.location.setText(list.get(position).getLocation());
        NumberFormat formatter=NumberFormat.getCurrencyInstance(new Locale("en","IN"));
        formatter.setMaximumFractionDigits(0);
        int parsed_price=Integer.parseInt(list.get(position).getStarting_price());
        String sPrice_formatted=formatter.format(parsed_price);

        holder.starting_price.setText(sPrice_formatted);
        double rat=Double.parseDouble(list.get(position).getRating());
        holder.ratingBar.setRating((float)rat);
        holder.main_rating_text.setText(rat+"/5");
        //Log.d("coordinates",list.get(position).coordinates);

        Double latitude=Double.parseDouble(list.get(position).getCoordinates().split(":")[0]);
        Double longitude=Double.parseDouble(list.get(position).getCoordinates().split(":")[1]);
        //Log.d("current cooordinates",lat+" "+lng);
        Double d=DistanceCalculator.getDistanceInKm(lat,lng,latitude,longitude);
        holder.distance.setText(String.format("%.2f",d)+" Km away");
        list.get(position).distance_in_km=d;
//        Log.d("distance",String.valueOf(d));


    }
    public void updateList(ArrayList<main_pg_class> list)
    {
        this.list=list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
