package com.decorator1889.cripta.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsModel {



    @Expose
    @SerializedName("imageurl")
    var imageurl = ""

    @Expose
    @SerializedName("title")
    var title = ""

    @Expose
    @SerializedName("url")
    var url = ""

    @Expose
    @SerializedName("body")
    var body = ""

    @Expose
    @SerializedName("source")
    var source = ""
}