package com.eleganzit.farmingpartner.model;

public class NotificationData {

    String name,date,statusData,vegetable_id,plot_name,veg_name,customer_id,veg_cal_status_id,farm_name,farm_photo;

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    public String getFarm_photo() {
        return farm_photo;
    }

    public void setFarm_photo(String farm_photo) {
        this.farm_photo = farm_photo;
    }

    public String getVeg_cal_status_id() {
        return veg_cal_status_id;
    }

    public void setVeg_cal_status_id(String veg_cal_status_id) {
        this.veg_cal_status_id = veg_cal_status_id;
    }

    public NotificationData(String date, String statusData, String vegetable_id, String plot_name, String veg_name, String customer_id) {
        this.date = date;
        this.statusData = statusData;
        this.vegetable_id = vegetable_id;
        this.plot_name = plot_name;
        this.veg_name = veg_name;
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatusData() {
        return statusData;
    }

    public void setStatusData(String statusData) {
        this.statusData = statusData;
    }

    public String getVegetable_id() {
        return vegetable_id;
    }

    public void setVegetable_id(String vegetable_id) {
        this.vegetable_id = vegetable_id;
    }

    public String getPlot_name() {
        return plot_name;
    }

    public void setPlot_name(String plot_name) {
        this.plot_name = plot_name;
    }

    public String getVeg_name() {
        return veg_name;
    }

    public void setVeg_name(String veg_name) {
        this.veg_name = veg_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}

