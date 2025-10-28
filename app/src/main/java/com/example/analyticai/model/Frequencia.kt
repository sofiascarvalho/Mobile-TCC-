package com.example.analyticai.model

data class Frequencia(
    val porcentagem_frequencia: String,
    val presencas: Int,
    val faltas: Int,
    val total_aulas: Int
)