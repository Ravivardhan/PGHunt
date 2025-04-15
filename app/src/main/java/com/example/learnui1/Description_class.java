package com.example.learnui1;

public class Description_class {
    String description_image;
    String sharing_count,price;
public Description_class()
{

}
    public Description_class(String description_image, String sharing_count, String price) {
        this.description_image = description_image;
        this.sharing_count = sharing_count;
        this.price = price;
    }

    public String getDescription_image() {

        return description_image;
    }

    public void setDescription_image(String description_image) {
        this.description_image = description_image;
    }

    public String getSharing_count() {
        return sharing_count;
    }

    public void setSharing_count(String sharing_count) {
        this.sharing_count = sharing_count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
