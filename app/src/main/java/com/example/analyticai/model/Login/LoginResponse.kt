package com.example.analyticai.model.Login

import com.example.analyticai.model.Login.Usuario

data class LoginResponse(
    val status: Boolean,
    val status_code: Int,
    val usuario: Usuario? // Pode ser nulo em caso de erro no login
)