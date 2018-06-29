package com.practice.project.android_bootcamp.model;

import android.media.Image;

import java.util.List;

public class Venue {

    private String id;
    private String address;
    private String name;
    private Image photo;
    private double longitude;
    private double latitude;
    private Category category;

    public Venue() {
    }

    public Venue(String id, String address, String name, Image photo, double longitude,
                 double latitude, Category category) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.photo = photo;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
