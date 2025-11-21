package com.example.analyticai.model.Recursos

data class Recurso(
    val id_aluno: Int,
    val id_turma: Int,
    val turma: String,
    val id_recursos: Int,
    val titulo: String,
    val descricao: String,
    val link_criterio: String,
    val data_criacao: String,
    val id_materia: Int,
    val materia: String,
    val id_professor: Int,
    val nome: String,
    val id_semestre: Int,
    val semestre: String
)
