package com.practice.project.android_bootcamp.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class JsonResponse implements Serializable {

    @Expose
    private Response response = new Response();

    public JsonResponse(){
    }

    public Response getResponse() {
        return response;
    }
}
