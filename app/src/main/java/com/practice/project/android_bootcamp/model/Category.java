package com.practice.project.android_bootcamp.model;

import java.util.List;

public class Category {

    private int id;
    private String name;
    private List<Venue> venues;

    public Category() {
    }

    public Category(int id, String name, List<Venue> venues) {
        this.id = id;
        this.name = name;
        this.venues = venues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
