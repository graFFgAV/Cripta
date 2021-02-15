package com.decorator1889.cripta.Models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsModelResponce (

        @Expose
        @SerializedName("Data")
        val data: ArrayList<NewsModel>

) : Serializable