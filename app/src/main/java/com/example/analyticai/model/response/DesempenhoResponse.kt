package com.example.analyticai.model.response

import com.example.analyticai.model.response.FrequenciaResponse

data class DesempenhoResponse(
    val aluno: AlunoResponse,
    val frequencia: FrequenciaResponse,
    val materia: MateriaResponse,
    val atividades: List<AtividadeResponse>,
    val media: String
)