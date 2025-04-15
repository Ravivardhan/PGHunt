package com.example.learnui1.ui.home;

import android.util.Log;

public class main_pg_class {
    String main_name,location;
    String rating,coordinates,category;
    Double distance_in_km=0.0;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String starting_price,main_image;
    //must have empty constructor for firebase
    public main_pg_class() {
    }

    public main_pg_class(String main_name, String location, String starting_price, String main_image,String rating,String coordinates,String category) {
        this.main_name = main_name;
        this.location = location;
        this.starting_price = starting_price;
        this.main_image = main_image;
        this.rating=rating;
        //Log.d("cc",coordinates);
        this.coordinates=coordinates;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getMain_name() {
        return main_name;
    }

    public void setMain_name(String main_name) {
        this.main_name = main_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }
}
