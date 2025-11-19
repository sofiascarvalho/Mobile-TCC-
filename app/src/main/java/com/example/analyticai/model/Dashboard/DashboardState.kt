package com.example.analyticai.model.Dashboard

data class DashboardState(
    val dashboard: DesempenhoResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPlaceholder: Boolean = true
)
