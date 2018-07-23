
package com.practice.project.android_bootcamp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(foreignKeys =
        {@ForeignKey(entity = Category.class,  parentColumns = "category_id", childColumns = "category_id",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Location.class, parentColumns = "location_id", childColumns = "location_id",
        onDelete = ForeignKey.CASCADE)})
public class Venue implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "venue_id")
    private int venueId;
    @Expose
    private String id;
    @Expose
    private String name;
    @Ignore
    @Expose
    private Location location = new Location();
    @Ignore
    @Expose
    private List<Category> categories = new ArrayList<>();
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "location_id")
    private int locationId;

    public Venue() {
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
