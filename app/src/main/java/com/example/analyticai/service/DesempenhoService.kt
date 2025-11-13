package com.example.analyticai.service

import com.example.analyticai.model.Dashboard.DashboardResponse
import com.example.analyticai.model.Login.LoginRequest
import com.example.analyticai.model.Login.LoginResponse
import com.example.analyticai.model.Login.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DesempenhoService {
    @POST("usuarios/login")
    suspend fun getAluno(@Body loginRequest: LoginRequest): LoginResponse

    @GET("desempenho/aluno/{idAluno}")
    suspend fun getDesempenho(
        @Path("idAluno") idAluno: Int,
        @Query("materia") idMateria: Int? = null,
        @Query("semestre") idSemestre: Int? = null
    ): DashboardResponse
}
