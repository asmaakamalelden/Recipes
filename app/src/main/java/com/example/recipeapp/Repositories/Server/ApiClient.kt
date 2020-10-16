package com.example.weatherloggerapp.Repositories.Server

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    var BASEURL = "https://hf-android-app.s3-eu-west-1.amazonaws.com"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }


    companion object {
        fun getInstance(): ApiClient? {
            var mInstance: ApiClient? = null
            if (mInstance == null) {
                mInstance = ApiClient()
            }
            return mInstance
        }
    }
    val getApi by lazy {
       getClientApi()
    }

    fun getClientApi(): ApiInterface? {
        return getClient()!!.create(ApiInterface::class.java)
    }

}