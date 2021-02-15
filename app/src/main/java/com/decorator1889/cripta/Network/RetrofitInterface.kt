package com.bestseller.coupons.sam.Network

import com.decorator1889.cripta.Models.DeFIModelResponce
import com.decorator1889.cripta.Models.GraphModelResponce
import com.decorator1889.cripta.Models.MarketsModelResponce
import com.decorator1889.cripta.Models.NewsModelResponce

import io.reactivex.Single
import retrofit2.http.*

interface   RetrofitInterface{

    @GET("v2/assets")
    fun getMarketsAll(): Single<MarketsModelResponce>

    @GET("crypto-defi.json")
    fun getDeFi(): Single<DeFIModelResponce>

    @GET("crypto-all.json")
    fun getMarkets(): Single<DeFIModelResponce>

    @GET("v2/assets/{id}/history?interval=d1")
    fun getMarketsType(@Path("id") id: String): Single<GraphModelResponce>

    @GET("v2/assets/{id}/history?interval=h1")
    fun getDay(@Path("id") id: String): Single<GraphModelResponce>

    @GET("v2/assets/{id}/history?interval=m1")
    fun getHour(@Path("id") id: String): Single<GraphModelResponce>

    @GET("data/v2/news/?lang=EN")
    fun getNews(): Single<NewsModelResponce>
}