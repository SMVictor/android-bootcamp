package com.practice.project.android_bootcamp.model;

import android.media.Image;

import java.util.List;

public class Venue {

    private int id;
    private String address;
    private String name;
    private Image photo;
    private double score;
    private double longitude;
    private double latitude;
    private Category category;

    public Venue() {
    }

    public Venue(int id, String address, String name, Image photo, double score, double longitude,
                 double latitude, Category category) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.photo = photo;
        this.score = score;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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
