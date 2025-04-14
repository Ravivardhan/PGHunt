package com.example.learnui1.ui.home;

public class main_pg_class {
    String main_name,location;
    String rating;


    public Double getLatitude() {
        return latitude;
    }



    public Double getLongitude() {
        return longitude;
    }



    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String starting_price,main_image;
    String coordinates;
    double latitude;
    double longitude;
    //must have empty constructor for firebase
    public main_pg_class() {
    }

    public main_pg_class(String main_name, String location, String starting_price, String main_image,String rating,String coordinates) {
        this.main_name = main_name;
        this.location = location;
        this.starting_price = starting_price;
        this.main_image = main_image;
        this.rating=rating;
        this.coordinates=coordinates;
        set_lat_lng(coordinates);
    }
    public void set_lat_lng(String coordinates) {
        if (coordinates != null) {
            String[] parts = coordinates.split(":");
            if (parts.length == 2) {
                try {
                    latitude = Double.parseDouble(parts[0]);
                    longitude = Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    latitude = 0;
                    longitude = 0;
                }
            }
        }
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
