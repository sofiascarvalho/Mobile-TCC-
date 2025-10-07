package com.example.analyticai.model

data class LoginResponse(
    val status_code: Int,
    val dados: List<Usuario>?,
    val message: String?
)
