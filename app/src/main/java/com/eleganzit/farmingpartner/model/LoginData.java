package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("farm_id")
    @Expose
    private String farm_id;

    public String getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(String farm_id) {
        this.farm_id = farm_id;
    }

    @SerializedName("farming_partner_id")
    @Expose
    private String farming_partner_id;
    @SerializedName("farming_partner_name")
    @Expose
    private String farming_partner_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("calender")
    @Expose
    private String calender;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("sub_location")
    @Expose
    private String sub_location;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("device_id")
    @Expose
    private String device_id;

    @SerializedName("device_token")
    @Expose
    private String device_token;

    public String getFarming_partner_id() {
        return farming_partner_id;
    }

    public void setFarming_partner_id(String farming_partner_id) {
        this.farming_partner_id = farming_partner_id;
    }

    public String getFarming_partner_name() {
        return farming_partner_name;
    }

    public void setFarming_partner_name(String farming_partner_name) {
        this.farming_partner_name = farming_partner_name;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getSub_location() {
        return sub_location;
    }

    public void setSub_location(String sub_location) {
        this.sub_location = sub_location;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
