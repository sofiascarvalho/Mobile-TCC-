package com.example.analyticai.service

import retrofit2.http.GET
import com.example.analyticai.model.Dashboards.MateriaResponse
import com.example.analyticai.model.Dashboards.SemestreResponse

interface FiltrosApi {

    @GET("semestre")
    suspend fun getSemestres(): SemestreResponse

    @GET("materia")
    suspend fun getMaterias(): MateriaResponse
}