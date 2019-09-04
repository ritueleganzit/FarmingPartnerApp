package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVegDetail {


    @SerializedName("vegetable_id")
    @Expose
    private String vegetableId;
    @SerializedName("veg_cat_id")
    @Expose
    private String vegCatId;
    @SerializedName("veg_image")
    @Expose
    private String vegImage;
    @SerializedName("veg_name")
    @Expose
    private String vegName;
    @SerializedName("sapling_date")
    @Expose
    private String saplingDate;
    @SerializedName("deweeding1")
    @Expose
    private String deweeding1;
    @SerializedName("deweeding2")
    @Expose
    private String deweeding2;
    @SerializedName("deweeding3")
    @Expose
    private String deweeding3;
    @SerializedName("fertilizing1")
    @Expose
    private String fertilizing1;
    @SerializedName("fertilizing2")
    @Expose
    private String fertilizing2;
    @SerializedName("fertilizing3")
    @Expose
    private String fertilizing3;
    @SerializedName("harvesting")
    @Expose
    private String harvesting;

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }

    public String getVegCatId() {
        return vegCatId;
    }

    public void setVegCatId(String vegCatId) {
        this.vegCatId = vegCatId;
    }

    public String getVegImage() {
        return vegImage;
    }

    public void setVegImage(String vegImage) {
        this.vegImage = vegImage;
    }

    public String getVegName() {
        return vegName;
    }

    public void setVegName(String vegName) {
        this.vegName = vegName;
    }

    public String getSaplingDate() {
        return saplingDate;
    }

    public void setSaplingDate(String saplingDate) {
        this.saplingDate = saplingDate;
    }

    public String getDeweeding1() {
        return deweeding1;
    }

    public void setDeweeding1(String deweeding1) {
        this.deweeding1 = deweeding1;
    }

    public String getDeweeding2() {
        return deweeding2;
    }

    public void setDeweeding2(String deweeding2) {
        this.deweeding2 = deweeding2;
    }

    public String getDeweeding3() {
        return deweeding3;
    }

    public void setDeweeding3(String deweeding3) {
        this.deweeding3 = deweeding3;
    }

    public String getFertilizing1() {
        return fertilizing1;
    }

    public void setFertilizing1(String fertilizing1) {
        this.fertilizing1 = fertilizing1;
    }

    public String getFertilizing2() {
        return fertilizing2;
    }

    public void setFertilizing2(String fertilizing2) {
        this.fertilizing2 = fertilizing2;
    }

    public String getFertilizing3() {
        return fertilizing3;
    }

    public void setFertilizing3(String fertilizing3) {
        this.fertilizing3 = fertilizing3;
    }

    public String getHarvesting() {
        return harvesting;
    }

    public void setHarvesting(String harvesting) {
        this.harvesting = harvesting;
    }

}
