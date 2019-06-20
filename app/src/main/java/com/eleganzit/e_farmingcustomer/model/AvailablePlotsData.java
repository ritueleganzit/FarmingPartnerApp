package com.eleganzit.e_farmingcustomer.model;

public class AvailablePlotsData {

    String photo,name;

    public AvailablePlotsData(String photo, String name) {
        this.photo = photo;
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
