package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePhoto {
    @SerializedName("farming_partner_id")
    @Expose
    private String farmingPartnerId;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getFarmingPartnerId() {
        return farmingPartnerId;
    }

    public void setFarmingPartnerId(String farmingPartnerId) {
        this.farmingPartnerId = farmingPartnerId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
