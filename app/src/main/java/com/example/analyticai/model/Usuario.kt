package com.example.analyticai.model

data class Usuario(
    val id_usuario: Int,
    val credencial: String,
    val nivel_usuario:String,
    val nome:String?,
    val email:String?,
    val telefone:String?
)