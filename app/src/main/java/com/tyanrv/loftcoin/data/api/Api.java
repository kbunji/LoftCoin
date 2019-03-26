package com.tyanrv.loftcoin.data.api;

import com.tyanrv.loftcoin.data.api.model.RateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    String CONVERT = "USD,EUR,RUB";

    @GET("v1/cryptocurrency/listings/latest")
    @Headers("X-CMC_PRO_API_KEY: 183d7ad1-d5f7-4440-a2aa-210696aa8587")
    Call<RateResponse> rates(@Query("convert") String convert);
}
