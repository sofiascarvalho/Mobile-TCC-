package com.example.analyticai.service

import com.example.analyticai.model.Ranking.RankingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RankingService {
    @GET("ranking/aluno/{perfilId}")
    suspend fun getRanking(
        @Path("perfilId") perfilId: Int,
        @Query("materia") materiaId: Int,
        @Query("semestre") semestreId: Int
    ): RankingResponse
}

