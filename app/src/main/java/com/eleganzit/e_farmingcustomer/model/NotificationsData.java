package com.eleganzit.e_farmingcustomer.model;

public class NotificationsData
{
    String photo,title,status;

    public NotificationsData(String photo, String title, String status) {
        this.photo = photo;
        this.title = title;
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
