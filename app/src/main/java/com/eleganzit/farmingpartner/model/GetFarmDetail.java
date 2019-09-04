package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFarmDetail {

    @SerializedName("farm_id ")
    @Expose
    private String farmId;
    @SerializedName("farm_name ")
    @Expose
    private String farmName;
    @SerializedName("owner_name ")
    @Expose
    private String ownerName;
    @SerializedName("purched_on ")
    @Expose
    private String purchedOn;
    @SerializedName("farm_photo ")
    @Expose
    private String farmPhoto;
    @SerializedName("farm_description ")
    @Expose
    private String farmDescription;
    @SerializedName("farm_address ")
    @Expose
    private String farmAddress;
    @SerializedName("farm_location ")
    @Expose
    private String farmLocation;
    @SerializedName("plot_capacity ")
    @Expose
    private String plotCapacity;
    @SerializedName("farm_long ")
    @Expose
    private String farmLong;
    @SerializedName("farm_lat ")
    @Expose
    private String farmLat;
    @SerializedName("admin_vegetablelist")
    @Expose
    private List<AdminVegetablelist> adminVegetablelist = null;

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPurchedOn() {
        return purchedOn;
    }

    public void setPurchedOn(String purchedOn) {
        this.purchedOn = purchedOn;
    }

    public String getFarmPhoto() {
        return farmPhoto;
    }

    public void setFarmPhoto(String farmPhoto) {
        this.farmPhoto = farmPhoto;
    }

    public String getFarmDescription() {
        return farmDescription;
    }

    public void setFarmDescription(String farmDescription) {
        this.farmDescription = farmDescription;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    public String getFarmLocation() {
        return farmLocation;
    }

    public void setFarmLocation(String farmLocation) {
        this.farmLocation = farmLocation;
    }

    public String getPlotCapacity() {
        return plotCapacity;
    }

    public void setPlotCapacity(String plotCapacity) {
        this.plotCapacity = plotCapacity;
    }

    public String getFarmLong() {
        return farmLong;
    }

    public void setFarmLong(String farmLong) {
        this.farmLong = farmLong;
    }

    public String getFarmLat() {
        return farmLat;
    }

    public void setFarmLat(String farmLat) {
        this.farmLat = farmLat;
    }

    public List<AdminVegetablelist> getAdminVegetablelist() {
        return adminVegetablelist;
    }

    public void setAdminVegetablelist(List<AdminVegetablelist> adminVegetablelist) {
        this.adminVegetablelist = adminVegetablelist;
    }


}
