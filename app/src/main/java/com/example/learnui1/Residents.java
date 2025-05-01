package com.example.learnui1;

public class Residents {
    private String username;
    private String imageURL;
//important for firebase -- must have empty constructor
    public Residents()
    {

    }
    public Residents(String username, String imageURL) {
        this.username = username;
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
