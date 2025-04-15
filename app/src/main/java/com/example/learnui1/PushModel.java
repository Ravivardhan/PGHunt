package com.example.learnui1;

import java.util.ArrayList;

public class PushModel {
    String address,category,coordinates,location,main_image, main_name,owner_name,pg_name,rating,rules,starting_price;
    ArrayList<Description_class> description;

    public PushModel()
    {

    }
    public PushModel(String address, String category, String coordinates, String location, String main_image, String main_name, String owner_name, String pg_name, String rating, String rules, String starting_price, ArrayList<Description_class> description) {
        this.address = address;
        this.category = category;
        this.coordinates = coordinates;
        this.location = location;
        this.main_image = main_image;
        this.main_name = main_name;
        this.owner_name = owner_name;
        this.pg_name = pg_name;
        this.rating = rating;
        this.rules = rules;
        this.starting_price = starting_price;
        this.description = description;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public String getMain_name() {
        return main_name;
    }

    public void setMain_name(String main_name) {
        this.main_name = main_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPg_name() {
        return pg_name;
    }

    public void setPg_name(String pg_name) {
        this.pg_name = pg_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public ArrayList<Description_class> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<Description_class> description) {
        this.description = description;
    }
}
