package com.example.learnui1;

public class display_pg_details_class {
    String pg_name,address,owner_name,category,rules,coordinates,mobile;

    public display_pg_details_class(String pg_name, String address, String owner_name, String category, String rules,String coordinates,String mobile) {
        this.pg_name = pg_name;
        this.address = address;
        this.owner_name = owner_name;
        this.category = category;
        this.rules = rules;
        this.coordinates=coordinates;
        this.mobile=mobile;
    }
    public display_pg_details_class()
    {

    }

    public String getPg_name() {
        return pg_name;
    }

    public void setPg_name(String pg_name) {
        this.pg_name = pg_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
