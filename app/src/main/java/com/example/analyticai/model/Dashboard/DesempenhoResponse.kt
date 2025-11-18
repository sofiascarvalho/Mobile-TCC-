package com.example.analyticai.model.Dashboard

import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.model.MateriaResponse
import com.example.analyticai.model.Dashboard.FrequenciaResponse

data class DesempenhoResponse(
    val aluno: List<Usuario>,
    val id_semestre: Int,
    val semestre: String,
    val frequencia: FrequenciaResponse,
    val materia: MateriaResponse,
    val atividades: List<AtividadeResponse>,
    val media: String
)
