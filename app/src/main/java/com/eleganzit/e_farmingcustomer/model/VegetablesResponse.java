package com.eleganzit.e_farmingcustomer.model;

import com.eleganzit.e_farmingcustomer.ExcercisePojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VegetablesResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ExcercisePojo> data = null;
    @SerializedName("admin_vegetablelist")
    @Expose
    private List<ExcercisePojo> admin_vegetablelist = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExcercisePojo> getData() {
        return data;
    }

    public void setData(List<ExcercisePojo> data) {
        this.data = data;
    }

    public List<ExcercisePojo> getAdminVegList() {
        return admin_vegetablelist;
    }

    public void setAdminVegList(List<ExcercisePojo> data) {
        this.admin_vegetablelist = data;
    }
}
