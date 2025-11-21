package com.example.analyticai.service

import com.example.analyticai.model.Recursos.RecursoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecursosService {

    @GET("recurso/aluno/{idAluno}")
    suspend fun getRecursos(
        @Path("idAluno") idAluno: Int,
        @Query("materia") materiaId: Int,
        @Query("semestre") semestreId: Int
    ): RecursoResponse
}
