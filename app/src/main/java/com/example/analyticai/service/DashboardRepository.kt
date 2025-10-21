package com.example.analyticai.service

import com.example.analyticai.model.DashboardResponse

class DashboardRepository {
    private val service = RetrofitFactory
        .getRetrofit()
        .create(DashboardService::class.java)

    suspend fun getDesempenhoAluno(idAluno: Int): DashboardResponse {
        return service.getDesempenhoAluno(idAluno)
    }
}
