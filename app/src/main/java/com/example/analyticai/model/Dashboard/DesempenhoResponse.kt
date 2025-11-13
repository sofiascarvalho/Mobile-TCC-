package com.example.analyticai.model.Dashboard

import com.example.analyticai.model.Login.Usuario

data class DesempenhoResponse(
    val aluno: List<Usuario>,
    val semestre: String,
    val frequencia: FrequenciaResponse,
    val materia: MateriaResponse,
    val atividades: List<AtividadeResponse>,
    val media: String
)
