package com.example.analyticai.service

import com.example.analyticai.model.DashboardResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DashboardService {
    @Headers("Content-Type: application/json")
    @GET("desempenho/aluno/{idAluno}")
    suspend fun getDesempenhoAluno(@Path("idAluno") idAluno: Int): DashboardResponse
}

