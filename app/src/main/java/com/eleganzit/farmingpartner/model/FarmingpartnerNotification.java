package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmingpartnerNotification {
    @SerializedName("farm_id")
    @Expose
    private String farmId;
    @SerializedName("farming_partner_id")
    @Expose
    private String farmingPartnerId;

    @SerializedName("customer_id")
    @Expose
    private String customer_id;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("veg_calendar_id")
    @Expose
    private String vegCalendarId;
    @SerializedName("veg_cal_status_id")
    @Expose
    private String vegCalStatusId;
    @SerializedName("veg_name")
    @Expose
    private String vegName;
    @SerializedName("plot_name")
    @Expose
    private String plotName;
    @SerializedName("vegetable_id")
    @Expose
    private String vegetableId;
    @SerializedName("farm_photo")
    @Expose
    private String farm_photo;

    public String getFarm_photo() {
        return farm_photo;
    }

    public void setFarm_photo(String farm_photo) {
        this.farm_photo = farm_photo;
    }

    @SerializedName("statusData")
    @Expose
    private List<StatusDatum> statusData = null;

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmingPartnerId() {
        return farmingPartnerId;
    }

    public void setFarmingPartnerId(String farmingPartnerId) {
        this.farmingPartnerId = farmingPartnerId;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getVegCalendarId() {
        return vegCalendarId;
    }

    public void setVegCalendarId(String vegCalendarId) {
        this.vegCalendarId = vegCalendarId;
    }

    public String getVegCalStatusId() {
        return vegCalStatusId;
    }

    public void setVegCalStatusId(String vegCalStatusId) {
        this.vegCalStatusId = vegCalStatusId;
    }

    public String getVegName() {
        return vegName;
    }

    public void setVegName(String vegName) {
        this.vegName = vegName;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }

    public List<StatusDatum> getStatusData() {
        return statusData;
    }

    public void setStatusData(List<StatusDatum> statusData) {
        this.statusData = statusData;
    }


}
