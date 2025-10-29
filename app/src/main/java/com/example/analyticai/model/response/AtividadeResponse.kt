package com.example.analyticai.model.response

data class AtividadeResponse(
    val idAtividade: Int,
    val atividade: String,
    val categoria: String,
    val nota: Double,
    val descricao: String
)