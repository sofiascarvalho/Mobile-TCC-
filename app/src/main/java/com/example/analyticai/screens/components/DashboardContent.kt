package com.example.analyticai.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.analyticai.model.Dashboard.DesempenhoResponse

@Composable
fun DashboardContent(
    dashboard: DesempenhoResponse,
    isPlaceholder: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val subjectName = dashboard.materia.materia

        PerformanceKpiCard(
            score = dashboard.media.toDoubleOrNull() ?: 0.0,
            subjectName = subjectName,
            modifier = Modifier.fillMaxWidth(),
            isPlaceholder = isPlaceholder
        )

        val frequenciaValue = dashboard.frequencia.porcentagem_frequencia
            .replace("%", "")
            .toFloatOrNull() ?: 0f

        FrequencyKpiCard(
            presentPercentage = frequenciaValue,
            modifier = Modifier.fillMaxWidth(),
            isPlaceholder = isPlaceholder
        )

        val barData = dashboard.atividades.map {
            BarData(it.atividade, it.nota)
        }

        SubjectPerformanceChartCard(
            subject = subjectName,
            data = barData,
            isPlaceholder = isPlaceholder
        )
    }
}
