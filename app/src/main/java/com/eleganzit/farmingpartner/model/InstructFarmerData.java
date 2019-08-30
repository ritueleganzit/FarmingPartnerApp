package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstructFarmerData {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("farming_partner_id")
    @Expose
    private String farmingPartnerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFarmingPartnerId() {
        return farmingPartnerId;
    }

    public void setFarmingPartnerId(String farmingPartnerId) {
        this.farmingPartnerId = farmingPartnerId;
    }
}
