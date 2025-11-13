package com.example.analyticai.model.Dashboard

data class DesempenhoResponse(
    val frequencia: FrequenciaResponse,
    val materia: MateriaResponse,
    val atividades: List<AtividadeResponse>,
    val media: String
)
