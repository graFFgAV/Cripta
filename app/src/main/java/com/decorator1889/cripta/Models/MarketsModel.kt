package com.decorator1889.cripta.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MarketsModel {
    @Expose
    @SerializedName("id")
    var id = ""

    @Expose
    @SerializedName("rank")
    var rank = ""

    @Expose
    @SerializedName("symbol")
    var symbol = ""

    @Expose
    @SerializedName("name")
    var name = ""

    @Expose
    @SerializedName("supply")
    var supply = ""

    @Expose
    @SerializedName("maxSupply")
    var maxSupply = ""

    @Expose
    @SerializedName("marketCapUsd")
    var marketCapUsd = ""

    @Expose
    @SerializedName("volumeUsd24Hr")
    var volumeUsd24Hr = ""

    @Expose
    @SerializedName("priceUsd")
    var priceUsd = ""

    @Expose
    @SerializedName("changePercent24Hr")
    var changePercent24Hr = ""

    @Expose
    @SerializedName("vwap24Hr")
    var vwap24Hr = ""

}