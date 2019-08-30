package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactOfficeData {
    @SerializedName("farming_partner_id")
    @Expose
    private String farming_partner_id;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email_id")
    @Expose
    private String email_id;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subject")
    @Expose
    private String subject;

    public String getFarming_partner_id() {
        return farming_partner_id;
    }

    public void setFarming_partner_id(String farming_partner_id) {
        this.farming_partner_id = farming_partner_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomerId() {
        return farming_partner_id;
    }

    public void setCustomerId(String customerId) {
        this.farming_partner_id = customerId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
