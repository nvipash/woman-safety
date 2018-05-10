package com.alexia.callbutton.jsonparsers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String URL = "192.168.0.103:9090";
    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static InfoService getInfoService(){
        return getRetrofitInstance().create(InfoService.class);
    }
}
