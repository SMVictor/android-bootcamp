package com.practice.project.android_bootcamp.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class JsonResponse implements Serializable {

    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
