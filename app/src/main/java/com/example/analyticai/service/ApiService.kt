package com.example.analyticai.service

import com.example.analyticai.model.DashboardResponse
import com.example.analyticai.model.DesempenhoResponse
import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("usuarios/login")
    suspend fun loginUsuario(@Body request: LoginRequest): Response<LoginResponse>

    @GET("v1/analytica-ai/desempenho/aluno/{idAluno}")
    suspend fun getDesempenhoAluno(
        @Path("idAluno") idAluno: Int,
        @Query("materia") materiaId: Int?,
        @Query("semestre") semestreId: Int?
    ): DesempenhoResponse


}