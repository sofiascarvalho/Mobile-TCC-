package com.example.analyticai.model.Dashboards

// Modelos para a resposta de Matérias
data class MateriaResponse(
    val status: Boolean,
    val status_code: Int,
    val items: Int,
    val materias: List<Materia>
)

data class Materia(
    val id_materia: Int,
    val materia: String,
    val cor_materia: String
)

// Modelos para a resposta de Semestres
data class SemestreResponse(
    val status: Boolean,
    val status_code: Int,
    val items: Int,
    val semestres: List<Semestre>
)

data class Semestre(
    val id_semestre: Int,
    val semestre: String
)