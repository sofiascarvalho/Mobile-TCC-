package com.example.analyticai.service

import com.example.analyticai.model.Login.LoginRequest
import com.example.analyticai.model.Login.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

interface LoginService {

    @Headers("Content-Type: application/json")
    @POST("usuarios/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}