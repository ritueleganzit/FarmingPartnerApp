package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailablePlotsData {

    @SerializedName("farming_partner_id")
    @Expose
    private String farmingPartnerId;
    @SerializedName("farming_partner_name")
    @Expose
    private String farmingPartnerName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("calender")
    @Expose
    private String calender;
    @SerializedName("register_date")
    @Expose
    private String registerDate;
    @SerializedName("farm_id")
    @Expose
    private String farmId;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("farm_photo")
    @Expose
    private String farmPhoto;
    @SerializedName("farm_description")
    @Expose
    private String farmDescription;
    @SerializedName("farm_address")
    @Expose
    private String farmAddress;
    @SerializedName("farm_location")
    @Expose
    private String farmLocation;
    @SerializedName("plot_capacity")
    @Expose
    private String plotCapacity;
    @SerializedName("remain_capacity")
    @Expose
    private String remain_capacity;

    public AvailablePlotsData(String farmingPartnerId, String farmingPartnerName, String email, String phone, String calender, String registerDate, String farmId, String farmName, String farmPhoto, String farmDescription, String farmAddress, String farmLocation, String plotCapacity, String remain_capacity) {
        this.farmingPartnerId = farmingPartnerId;
        this.farmingPartnerName = farmingPartnerName;
        this.email = email;
        this.phone = phone;
        this.calender = calender;
        this.registerDate = registerDate;
        this.farmId = farmId;
        this.farmName = farmName;
        this.farmPhoto = farmPhoto;
        this.farmDescription = farmDescription;
        this.farmAddress = farmAddress;
        this.farmLocation = farmLocation;
        this.plotCapacity = plotCapacity;
        this.remain_capacity = remain_capacity;
    }

    public String getFarmingPartnerId() {
        return farmingPartnerId;
    }

    public void setFarmingPartnerId(String farmingPartnerId) {
        this.farmingPartnerId = farmingPartnerId;
    }

    public String getFarmingPartnerName() {
        return farmingPartnerName;
    }

    public void setFarmingPartnerName(String farmingPartnerName) {
        this.farmingPartnerName = farmingPartnerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCalender() {
        return calender;
    }

    public void setCalender(String calender) {
        this.calender = calender;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

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

    public String getRemain_capacity() {
        return remain_capacity;
    }

    public void setRemain_capacity(String remain_capacity) {
        this.remain_capacity = remain_capacity;
    }
}
