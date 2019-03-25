package com.tyanrv.loftcoin.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("price")
    public Double price;

    @SerializedName("percent_change_1h")
    public Double percentChange1h;

    @SerializedName("percent_change_24h")
    public Double percentChange24h;

    @SerializedName("percent_change_7d")
    public Double percentChange7d;
}
