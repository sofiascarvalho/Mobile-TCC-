package com.example.analyticai.service

import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("usuarios/login")
    suspend fun loginUsuario(@Body request: LoginRequest): Response<LoginResponse>
}