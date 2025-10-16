package com.example.analyticai.service

import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object RetrofitFactory {
    private val BASE_URL = "http://192.168.56.1:8080/v1/analytica-ai/"

    private val retrofitFactory = retrofit2.Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): ApiService {
        return retrofitFactory.create(ApiService::class.java)
    }
}


