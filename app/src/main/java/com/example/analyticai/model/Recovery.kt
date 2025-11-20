package com.example.analyticai.model

data class RecoveryRequest(
    val credencial: String
)

data class RecoveryResponse(
    val status: Boolean,
    val status_code: Int,
    val message: String
)
