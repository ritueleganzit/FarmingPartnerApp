package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminVegetablesData {
    @SerializedName("vegetable_id")
    @Expose
    public String vegetable_id;
    @SerializedName("veg_cat_id")
    @Expose
    public String veg_cat_id;
    @SerializedName("veg_image")
    @Expose
    public String veg_image;
    @SerializedName("veg_name")
    @Expose
    public String veg_name;

    public AdminVegetablesData(String vegetable_id, String veg_image, String veg_name, String veg_cat_id) {
        this.vegetable_id = vegetable_id;
        this.veg_image = veg_image;
        this.veg_name = veg_name;
        this.veg_cat_id = veg_cat_id;
    }

    public String getVegetableId() {
        return vegetable_id;
    }

    public void setVegetableId(String vegetable_id) {
        this.vegetable_id = vegetable_id;
    }

    public String getVegCatId() {
        return veg_cat_id;
    }

    public void setVegCatId(String veg_cat_id) {
        this.veg_cat_id = veg_cat_id;
    }

    public String getVegImage() {
        return veg_image;
    }

    public void setVegImage(String veg_image) {
        this.veg_image = veg_image;
    }

    public String getVegName() {
        return veg_name;
    }

    public void setVegName(String veg_name) {
        this.veg_name = veg_name;
    }

}
