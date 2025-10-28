package com.example.analyticai.service

import com.example.analyticai.model.DashboardResponse

class DesempenhoRepository (private val api: ApiService){
    private val service = RetrofitFactory
        .getRetrofit()
        .create(DashboardService::class.java)

    suspend fun getDesempenho(idAluno: Int, materiaId: Int?, semestreId: Int?) =
        api.getDesempenhoAluno(idAluno, materiaId, semestreId)
}

