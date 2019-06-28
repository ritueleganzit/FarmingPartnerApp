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
}

