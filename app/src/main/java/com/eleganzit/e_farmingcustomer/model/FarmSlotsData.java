package com.eleganzit.e_farmingcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FarmSlotsData {
    @SerializedName("customer_plot_id")
    @Expose
    private String customerPlotId;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("vegetable_id")
    @Expose
    private String vegetable_id;
    @SerializedName("vegetable_name")
    @Expose
    private String vegetableName;

    public String getCustomerPlotId() {
        return customerPlotId;
    }

    public void setCustomerPlotId(String customerPlotId) {
        this.customerPlotId = customerPlotId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getVegetable_id() {
        return vegetable_id;
    }

    public void setVegetable_id(String vegetable_id) {
        this.vegetable_id = vegetable_id;
    }

    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }
}
