
package com.practice.project.android_bootcamp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venue implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "venue_id")
    private int venue_id;
    @Expose
    private String id;
    @Expose
    private String name;
    @Ignore
    @Expose
    private Location location = new Location();
    @Ignore
    @Expose
    private List<Category> categories = new ArrayList<Category>();

    public Venue() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id = venue_id;
    }
}
