package com.decorator1889.cripta.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DeFiModel (

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("symbol")
    var symbol: String = "",

    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("cmc_rank")
    var cmc_rank: String = "",

    @Expose
    @SerializedName("quote")
    val quote: quote

) : Serializable