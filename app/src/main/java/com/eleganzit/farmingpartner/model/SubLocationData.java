package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubLocationData {
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("parent_region")
    @Expose
    private String parentRegion;
    @SerializedName("sublocation_name")
    @Expose
    private String sublocationName;

    public SubLocationData(String locationId, String parentRegion, String sublocationName) {
        this.locationId = locationId;
        this.parentRegion = parentRegion;
        this.sublocationName = sublocationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public void setParentRegion(String parentRegion) {
        this.parentRegion = parentRegion;
    }

    public String getSublocationName() {
        return sublocationName;
    }

    public void setSublocationName(String sublocationName) {
        this.sublocationName = sublocationName;
    }
}
