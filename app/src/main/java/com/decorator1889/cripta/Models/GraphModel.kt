package com.decorator1889.cripta.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GraphModel {
    @Expose
    @SerializedName("priceUsd")
    var priceUsd = ""

    @Expose
    @SerializedName("time")
    var time = ""
}