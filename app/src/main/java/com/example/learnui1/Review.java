package com.example.learnui1;

public class Review {
    String username,review,review_time;
    String profile_image;

    public Review() {
    }

    public Review(String username, String review, String review_time, String profile_image) {
        this.username = username;
        this.review = review;
        this.review_time = review_time;
        this.profile_image = profile_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview_time() {
        return review_time;
    }

    public void setReview_time(String review_time) {
        this.review_time = review_time;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
