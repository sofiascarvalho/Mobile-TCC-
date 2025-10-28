package com.example.analyticai.service

import com.example.analyticai.model.DesempenhoAlunoResponse
import retrofit2.http.GET
import retrofit2.http.Path
interface DashboardService {

    @GET("/v1/analytica-ai/desempenho/aluno/{idAluno}")
    suspend fun getDesempenhoAluno(
        @Path("idAluno") idAluno: Int
    ): DesempenhoAlunoResponse
}


