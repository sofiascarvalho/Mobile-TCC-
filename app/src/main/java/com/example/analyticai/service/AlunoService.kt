package com.example.analyticai.service

import com.example.analyticai.model.Profile.UpdateAlunoRequest
import com.example.analyticai.model.Profile.UpdateAlunoResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlunoService {
    @Headers("Content-Type: application/json")
    @PUT("aluno/{perfilId}")
    suspend fun updateAluno(
        @Path("perfilId") perfilId: Int,
        @Body request: UpdateAlunoRequest
    ): UpdateAlunoResponse
}

