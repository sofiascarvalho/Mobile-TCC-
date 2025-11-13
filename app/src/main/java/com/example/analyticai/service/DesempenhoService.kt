package com.example.analyticai.service

import com.example.analyticai.model.Dashboard.DashboardResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DesempenhoService {
    @GET("/v1/analytica-ai/aluno/{id}")
    suspend fun getDesempenho(
        @Path("id") alunoId: String
    ): DashboardResponse
}
