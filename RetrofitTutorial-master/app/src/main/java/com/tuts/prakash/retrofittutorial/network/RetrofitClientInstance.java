package com.tuts.prakash.retrofittutorial.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//To make network requests to a REST API with Retrofit, we need to create the Retrofit Instance using the Retrofit.Builder class and configure it with a base URL.
public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    // Fake Online REST API for Testing and Prototyping https://jsonplaceholder.typicode.com/guide.html   tu dole
    // https://jsonplaceholder.typicode.com/photos

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
