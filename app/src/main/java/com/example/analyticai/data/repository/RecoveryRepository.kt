package com.example.analyticai.data.repository

import com.example.analyticai.model.RecoveryRequest
import com.example.analyticai.model.RecoveryResponse
import com.example.analyticai.service.RecoveryService
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Repositório responsável por gerenciar as operações de recuperação de senha.
 * Ele interage com o RecoveryService para enviar solicitações de recuperação.
 */
class RecoveryRepository @Inject constructor(
    private val recoveryService: RecoveryService
) {
    suspend fun recoverPassword(credencial: String): RecoveryResponse {
        val request = RecoveryRequest(credencial)
        val apiResponse = recoveryService.recoverPassword(request)
        
        if (!apiResponse.isSuccessful) {
            val errorBody = apiResponse.errorBody()?.string()
            val errorMessage = try {
                val errorResponse = Gson().fromJson(errorBody, RecoveryResponse::class.java)
                errorResponse.message ?: "Erro desconhecido"
            } catch (e: Exception) {
                "Erro ao processar a resposta do servidor"
            }
            throw Exception(errorMessage)
        }
        
        return apiResponse.body() ?: throw Exception("Resposta vazia do servidor")
    }
}
