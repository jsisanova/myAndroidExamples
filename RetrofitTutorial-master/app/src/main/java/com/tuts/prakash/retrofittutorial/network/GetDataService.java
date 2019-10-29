package com.tuts.prakash.retrofittutorial.network;

import com.tuts.prakash.retrofittutorial.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Define the Endpoints inside of an interface using special retrofit annotations to encode details about the parameters and request method.
// Retrofit turns your HTTP API into a Java interface.
public interface GetDataService {

    // Every method must have an HTTP annotation that provides the request method and relative URL.
    // You can also specify query parameters in the URL.     @GET("users/list?sort=desc")
    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}