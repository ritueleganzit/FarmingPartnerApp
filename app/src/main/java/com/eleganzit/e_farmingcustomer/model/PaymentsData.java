package com.eleganzit.e_farmingcustomer.model;

public class PaymentsData
{
    String title,cost;

    public PaymentsData(String title, String cost) {
        this.title = title;
        this.cost = cost;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
