package com.practice.project.android_bootcamp.utilities;

import com.practice.project.android_bootcamp.model.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FourSquareAPI {

    @GET("venues/search")
    Call<JsonResponse> requestSearch(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("v") String v,
            @Query("ll") String ll,
            @Query("radius") String radius);
}
