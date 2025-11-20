package com.example.analyticai.service

import com.example.analyticai.model.RecoveryRequest
import com.example.analyticai.model.RecoveryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RecoveryService {
    @Headers("Content-Type: application/json")
    @POST("usuarios/recuperar-senha")
    suspend fun recoverPassword(
        @Body request: RecoveryRequest
    ): Response<RecoveryResponse>
}
