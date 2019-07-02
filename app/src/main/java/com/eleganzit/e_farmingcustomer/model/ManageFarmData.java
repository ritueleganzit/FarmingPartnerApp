package com.eleganzit.e_farmingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManageFarmData {


    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("farm_id")
    @Expose
    private String farmId;
    @SerializedName("customer_plot_id")
    @Expose
    private String customer_plot_id;
    @SerializedName("plot_description")
    @Expose
    private String plotDescription;
    @SerializedName("sub_location")
    @Expose
    private String subLocation;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("payment_referance")
    @Expose
    private String paymentReferance;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("sapling_date")
    @Expose
    private String saplingDate;
    @SerializedName("delivery_date1")
    @Expose
    private String deliveryDate1;
    @SerializedName("delivery_date2")
    @Expose
    private String deliveryDate2;
    @SerializedName("delivery_date3")
    @Expose
    private String deliveryDate3;
    @SerializedName("delivery_date4")
    @Expose
    private String deliveryDate4;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("farm_photo")
    @Expose
    private String farmPhoto;
    @SerializedName("plot_name")
    @Expose
    private String plotName;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getCustomer_plot_id() {
        return customer_plot_id;
    }

    public void setCustomer_plot_id(String customer_plot_id) {
        this.customer_plot_id = customer_plot_id;
    }

    public String getPlotDescription() {
        return plotDescription;
    }

    public void setPlotDescription(String plotDescription) {
        this.plotDescription = plotDescription;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentReferance() {
        return paymentReferance;
    }

    public void setPaymentReferance(String paymentReferance) {
        this.paymentReferance = paymentReferance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSaplingDate() {
        return saplingDate;
    }

    public void setSaplingDate(String saplingDate) {
        this.saplingDate = saplingDate;
    }

    public String getDeliveryDate1() {
        return deliveryDate1;
    }

    public void setDeliveryDate1(String deliveryDate1) {
        this.deliveryDate1 = deliveryDate1;
    }

    public String getDeliveryDate2() {
        return deliveryDate2;
    }

    public void setDeliveryDate2(String deliveryDate2) {
        this.deliveryDate2 = deliveryDate2;
    }

    public String getDeliveryDate3() {
        return deliveryDate3;
    }

    public void setDeliveryDate3(String deliveryDate3) {
        this.deliveryDate3 = deliveryDate3;
    }

    public String getDeliveryDate4() {
        return deliveryDate4;
    }

    public void setDeliveryDate4(String deliveryDate4) {
        this.deliveryDate4 = deliveryDate4;
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

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

}
