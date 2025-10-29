package com.example.analyticai.model.response

import com.example.analyticai.model.response.DesempenhoResponse

data class DashboardResponse(
    val status: Boolean,
    val status_code: Int,
    val items: Int,
    val desempenho: List<DesempenhoResponse>
)