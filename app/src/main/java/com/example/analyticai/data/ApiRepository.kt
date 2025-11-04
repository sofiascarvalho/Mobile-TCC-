package com.example.analyticai.data

import com.example.analyticai.model.AlunoResponse
import com.example.analyticai.model.DashboardResponse
import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import com.example.analyticai.service.ApiService
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {

    // 🔹 Login do usuário
    suspend fun loginUsuario(request: LoginRequest): Response<LoginResponse> {
        return apiService.loginUsuario(request)
    }

    // 🔹 Buscar lista completa de alunos
    suspend fun getAlunos(): Response<AlunoResponse> {
        return apiService.getAlunos()
    }

    // 🔹 Buscar desempenho do aluno (exemplo de endpoint)
    suspend fun getDesempenhoAluno(idAluno: Int): DashboardResponse {
        return apiService.getDesempenhoAluno(idAluno)
    }
}
