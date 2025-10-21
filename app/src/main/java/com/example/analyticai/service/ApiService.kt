package com.example.analyticai.service

import com.example.analyticai.model.DashboardResponse
import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("usuarios/login")
    suspend fun loginUsuario(@Body request: LoginRequest): Response<LoginResponse>

    @GET("desempenho/turma/{idProfessor}")
    suspend fun getDesempenhoAluno(
        @Path("idAluno") idAluno: Int
    ): DashboardResponse
}