package com.example.analyticai.model.Dashboard

data class FrequenciaResponse(
    val porcentagem_frequencia: String,
    val presencas: Int,
    val faltas: Int,
    val total_aulas: Int
)
