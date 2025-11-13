package com.example.analyticai.service

import com.example.analyticai.model.Dashboard.DashboardResponse
import retrofit2.http.GET

interface DesempenhoService {
    @GET("desempenho/aluno")
    suspend fun getDesempenho(): DashboardResponse
}