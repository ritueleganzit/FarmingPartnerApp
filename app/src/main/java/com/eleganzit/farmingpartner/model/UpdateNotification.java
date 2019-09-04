package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateNotification {
    @SerializedName("veg_cal_status_id")
    @Expose
    private String vegCalStatusId;

    public String getVegCalStatusId() {
        return vegCalStatusId;
    }

    public void setVegCalStatusId(String vegCalStatusId) {
        this.vegCalStatusId = vegCalStatusId;
    }

}
