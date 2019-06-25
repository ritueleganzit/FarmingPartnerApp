package com.eleganzit.e_farmingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateData {
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
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("plot_name")
    @Expose
    private String plotName;
    @SerializedName("farm_id")
    @Expose
    private String farmId;
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
    @SerializedName("veg_list")
    @Expose
    private String vegList;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
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

    public String getVegList() {
        return vegList;
    }

    public void setVegList(String vegList) {
        this.vegList = vegList;
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
}
