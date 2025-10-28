package com.example.analyticai.model

data class DesempenhoResponse(
    val aluno: AlunoResponse,
    val frequencia: Frequencia,
    val materia: MateriaResponse,
    val atividades: List<Atividade>,
    val media: String
)
