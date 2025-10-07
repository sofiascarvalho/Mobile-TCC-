package com.example.analyticai.service

import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private val BASE_URL = "http://10.0.2.2:8080/v1/analytica-ai/usuarios/"

    private val retrofitFactory = retrofit2.Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): ApiService {
        return retrofitFactory.create(ApiService::class.java)
    }
}