package com.bestseller.coupons.sam.Network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {

    companion object {

        var retrofite = Retrofit.Builder().baseUrl("https://api.coincap.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        var retrofitNews = Retrofit.Builder().baseUrl("https://min-api.cryptocompare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        var retrofitDeFi = Retrofit.Builder().baseUrl("http://ovz1.9276540579.pr46m.vps.myjino.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


        @JvmStatic
    fun getRetrofit(): RetrofitInterface? {

        return retrofite.build().create(RetrofitInterface::class.java)
        }

            @JvmStatic
        fun getRetrofitNews(): RetrofitInterface? {

            return retrofitNews.build().create(RetrofitInterface::class.java)
        }

            @JvmStatic
        fun getRetrofitDeFi(): RetrofitInterface? {
            return retrofitDeFi.build().create(RetrofitInterface::class.java)
        }
    }
}