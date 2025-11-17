package com.example.analyticai.service

import retrofit2.http.GET
import com.example.analyticai.model.Dashboards.MateriaResponse
import com.example.analyticai.model.Dashboards.SemestreResponse

interface FiltrosApi {

    @GET("v1/analytica-ai/semestre")
    suspend fun getSemestres(): SemestreResponse

    @GET("v1/analytica-ai/materia")
    suspend fun getMaterias(): MateriaResponse
}