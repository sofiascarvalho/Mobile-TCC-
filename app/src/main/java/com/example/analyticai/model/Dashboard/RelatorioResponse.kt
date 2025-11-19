package com.example.analyticai.model.Dashboard

data class RelatorioGeradoResponse(
    val status_code: Int,
    val message: String,
    val relatorio: RelatorioGerado
)

data class RelatorioGerado(
    val link: String,
    val data: String
)
