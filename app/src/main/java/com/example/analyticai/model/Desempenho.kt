package com.example.analyticai.model

data class Desempenho(
    val aluno: Aluno,
    val frequencia: Frequencia,
    val materia_id: Int,
    val materia: String,
    val atividades: List<Atividade>,
    val media: Double
)