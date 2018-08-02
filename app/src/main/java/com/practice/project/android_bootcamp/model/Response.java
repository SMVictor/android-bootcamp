package com.practice.project.android_bootcamp.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable
{
    @Expose
    private List<Venue> venues = new ArrayList<>();

    public Response() {}

    public List<Venue> getVenues() {
        return venues;
    }

}