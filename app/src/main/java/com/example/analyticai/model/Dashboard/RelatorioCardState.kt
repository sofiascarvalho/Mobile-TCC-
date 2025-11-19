package com.example.analyticai.model.Dashboard

data class RelatorioCardState(
    val tipo: RelatorioTipo,
    val statusMessage: String,
    val lastUpdate: String? = null,
    val link: String? = null,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val hasError: Boolean = false
)

enum class RelatorioTipo(val titulo: String) {
    COMPLETO("Relatório Completo"),
    DESEMPENHO("Resumo de Desempenho"),
    FREQUENCIA("Relatório de Frequência")
}

