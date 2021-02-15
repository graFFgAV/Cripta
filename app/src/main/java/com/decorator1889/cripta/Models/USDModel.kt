package com.decorator1889.cripta.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class USDModel(

    @Expose
    @SerializedName("price")
    val price: String,

    @Expose
    @SerializedName("volume_24h")
    val volume_24h: String,

    @Expose
    @SerializedName("percent_change_1h")
    val percent_change_1h: String,

    @Expose
    @SerializedName("percent_change_24h")
    val percent_change_24h: String,

    @Expose
    @SerializedName("market_cap")
    val market_cap: String

) : Serializable