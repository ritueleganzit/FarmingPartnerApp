package com.eleganzit.farmingpartner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusDatum {

    @SerializedName("sapling_date")
    @Expose
    private String saplingDate;
    @SerializedName("deweeding1")
    @Expose
    private String deweeding1;
    @SerializedName("deweeding2")
    @Expose
    private String deweeding2;
    @SerializedName("deweeding3")
    @Expose
    private String deweeding3;
    @SerializedName("fertilizing1")
    @Expose
    private String fertilizing1;
    @SerializedName("fertilizing2")
    @Expose
    private String fertilizing2;
    @SerializedName("fertilizing3")
    @Expose
    private String fertilizing3;
    @SerializedName("harvesting")
    @Expose
    private String harvesting;
    @SerializedName("sapling_date_status")
    @Expose
    private String saplingDateStatus;
    @SerializedName("deweeding1_status")
    @Expose
    private String deweeding1Status;
    @SerializedName("deweeding2_status")
    @Expose
    private String deweeding2Status;
    @SerializedName("deweeding3_status")
    @Expose
    private String deweeding3Status;
    @SerializedName("fertilizing1_status")
    @Expose
    private String fertilizing1Status;
    @SerializedName("fertilizing2_status")
    @Expose
    private String fertilizing2Status;
    @SerializedName("fertilizing3_status")
    @Expose
    private String fertilizing3Status;
    @SerializedName("harvesting_status")
    @Expose
    private String harvestingStatus;



    public String getSaplingDate() {
        return saplingDate;
    }

    public void setSaplingDate(String saplingDate) {
        this.saplingDate = saplingDate;
    }

    public String getDeweeding1() {
        return deweeding1;
    }

    public void setDeweeding1(String deweeding1) {
        this.deweeding1 = deweeding1;
    }

    public String getDeweeding2() {
        return deweeding2;
    }

    public void setDeweeding2(String deweeding2) {
        this.deweeding2 = deweeding2;
    }

    public String getDeweeding3() {
        return deweeding3;
    }

    public void setDeweeding3(String deweeding3) {
        this.deweeding3 = deweeding3;
    }

    public String getFertilizing1() {
        return fertilizing1;
    }

    public void setFertilizing1(String fertilizing1) {
        this.fertilizing1 = fertilizing1;
    }

    public String getFertilizing2() {
        return fertilizing2;
    }

    public void setFertilizing2(String fertilizing2) {
        this.fertilizing2 = fertilizing2;
    }

    public String getFertilizing3() {
        return fertilizing3;
    }

    public void setFertilizing3(String fertilizing3) {
        this.fertilizing3 = fertilizing3;
    }

    public String getHarvesting() {
        return harvesting;
    }

    public void setHarvesting(String harvesting) {
        this.harvesting = harvesting;
    }

    public String getSaplingDateStatus() {
        return saplingDateStatus;
    }

    public void setSaplingDateStatus(String saplingDateStatus) {
        this.saplingDateStatus = saplingDateStatus;
    }

    public String getDeweeding1Status() {
        return deweeding1Status;
    }

    public void setDeweeding1Status(String deweeding1Status) {
        this.deweeding1Status = deweeding1Status;
    }

    public String getDeweeding2Status() {
        return deweeding2Status;
    }

    public void setDeweeding2Status(String deweeding2Status) {
        this.deweeding2Status = deweeding2Status;
    }

    public String getDeweeding3Status() {
        return deweeding3Status;
    }

    public void setDeweeding3Status(String deweeding3Status) {
        this.deweeding3Status = deweeding3Status;
    }

    public String getFertilizing1Status() {
        return fertilizing1Status;
    }

    public void setFertilizing1Status(String fertilizing1Status) {
        this.fertilizing1Status = fertilizing1Status;
    }

    public String getFertilizing2Status() {
        return fertilizing2Status;
    }

    public void setFertilizing2Status(String fertilizing2Status) {
        this.fertilizing2Status = fertilizing2Status;
    }

    public String getFertilizing3Status() {
        return fertilizing3Status;
    }

    public void setFertilizing3Status(String fertilizing3Status) {
        this.fertilizing3Status = fertilizing3Status;
    }

    public String getHarvestingStatus() {
        return harvestingStatus;
    }

    public void setHarvestingStatus(String harvestingStatus) {
        this.harvestingStatus = harvestingStatus;
    }

}
