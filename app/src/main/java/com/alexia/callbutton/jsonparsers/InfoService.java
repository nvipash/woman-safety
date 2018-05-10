package com.alexia.callbutton.jsonparsers;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InfoService {

    @GET("api/tests/info")
    Call<Info> getInfo();
}
