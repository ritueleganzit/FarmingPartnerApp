package com.eleganzit.e_farmingcustomer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sumeet on 16-07-2017.
 */

public class ExcercisePojo {

    @SerializedName("vegetable_id")
    @Expose
    public String exerciseId;
    @SerializedName("veg_cat_id")
    @Expose
    public String vegCatId;
    @SerializedName("veg_image")
    @Expose
    public String img;
    @SerializedName("veg_name")
    @Expose
    public String name;
    @SerializedName("local_language")
    @Expose
    public String localLanguage;
    @SerializedName("veg_calendar_id")
    @Expose
    private String vegCalendarId;
    @SerializedName("farm_id")
    @Expose
    private String farmId;
    @SerializedName("rotation")
    @Expose
    private String rotation;
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
    public ExcercisePojo(String exerciseId, String name, String img, String vegCatId, String localLanguage) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.img = img;
        this.vegCatId = vegCatId;
        this.localLanguage = localLanguage;
    }

    public String getVegetableId() {
        return exerciseId;
    }

    public void setVegetableId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getVegCatId() {
        return vegCatId;
    }

    public void setVegCatId(String vegCatId) {
        this.vegCatId = vegCatId;
    }

    public String getVegImage() {
        return img;
    }

    public void setVegImage(String vegImage) {
        this.img = vegImage;
    }

    public String getVegName() {
        return name;
    }

    public void setVegName(String vegName) {
        this.name = vegName;
    }

    public String getLocalLanguage() {
        return localLanguage;
    }

    public void setLocalLanguage(String localLanguage) {
        this.localLanguage = localLanguage;
    }


    public String getVegCalendarId() {
        return vegCalendarId;
    }

    public void setVegCalendarId(String vegCalendarId) {
        this.vegCalendarId = vegCalendarId;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
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

