package com.example.myotp.server.scripts;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.8.101/demo_pets/";
    private static Retrofit retrofit;

    static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
