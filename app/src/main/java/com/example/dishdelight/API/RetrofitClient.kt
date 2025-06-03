package com.example.dishdelight.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient
{

    private const val BASE_URL= "https://uat.onebanc.ai/"

    val api: OneBancApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OneBancApi::class.java)
    }
}