package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllVegetables {

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
    @SerializedName("local_language")
    @Expose
    private String localLanguage;

    public GetAllVegetables(String vegetableId, String vegCatId, String vegImage, String vegName, String localLanguage) {
        this.vegetableId = vegetableId;
        this.vegCatId = vegCatId;
        this.vegImage = vegImage;
        this.vegName = vegName;
        this.localLanguage = localLanguage;
    }

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

    public String getLocalLanguage() {
        return localLanguage;
    }

    public void setLocalLanguage(String localLanguage) {
        this.localLanguage = localLanguage;
    }
}
