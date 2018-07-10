package com.practice.project.android_bootcamp.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class Location implements Serializable
{
    @Expose
    private double lat;
    @Expose
    private double lng;
    @Expose
    private List<String> formattedAddress;

    public Location() {}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<String> getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(List<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}