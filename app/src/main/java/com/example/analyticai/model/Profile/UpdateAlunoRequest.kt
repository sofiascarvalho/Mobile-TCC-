package com.example.analyticai.model.Profile

data class UpdateAlunoRequest(
    val nome: String,
    val email: String,
    val telefone: String
)

data class UpdateAlunoResponse(
    val status: Boolean,
    val status_code: Int,
    val message: String
)

