package com.example.analyticai.model.Dashboard

import com.example.analyticai.model.MateriaResponse

data class DesempenhoResponse(
    val aluno: AlunoDesempenho?,
    val frequencia: FrequenciaResponse,
    val materia: MateriaResponse,
    val atividades: List<AtividadeResponse>,
    val media: String
)

data class AlunoDesempenho(
    val id_aluno: Int,
    val nome: String
)
