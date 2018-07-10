package com.practice.project.android_bootcamp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

@Entity
public class Location implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id")
    private int locationId;
    @Expose
    private double lat;
    @Expose
    private double lng;
    private String formattedAddressString;
    @Ignore
    @Expose
    private List<String> formattedAddress;

    public Location() {}

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

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

    public String getFormattedAddressString() {
        return formattedAddressString;
    }

    public void setFormattedAddressString(String formattedAddressString) {
        this.formattedAddressString = formattedAddressString;
    }
}