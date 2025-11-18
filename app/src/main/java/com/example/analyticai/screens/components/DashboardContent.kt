package com.example.analyticai.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.analyticai.model.Dashboard.DesempenhoResponse
import com.example.analyticai.screens.TextDark

@Composable
fun DashboardContent(
    dashboard: DesempenhoResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // KPI Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PerformanceKpiCard(
                score = dashboard.media.toDoubleOrNull() ?: 0.0,
                change = 1.2,
                modifier = Modifier.weight(1f)
            )
            
            val frequenciaValue = dashboard.frequencia.porcentagem_frequencia
                .replace("%", "")
                .toFloatOrNull() ?: 0f
            
            FrequencyKpiCard(
                presentPercentage = frequenciaValue,
                modifier = Modifier.weight(1f)
            )
        }
        
        // Gráfico de Desempenho
        val mockData = listOf(
            BarData("Prova 1", 8.5f),
            BarData("Prova 2", 7.8f),
            BarData("Trabalho", 9.2f),
            BarData("Seminário", 8.0f)
        )
        
        SubjectPerformanceChartCard(
            subject = dashboard.materia.materia,
            data = mockData
        )
        
        // Lista de Atividades
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Atividades Recentes",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Column {
                    dashboard.atividades.take(3).forEachIndexed { index, atividade ->
                        ActivityItem(atividade = atividade)
                        if (index < dashboard.atividades.take(3).size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                            androidx.compose.material3.Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = androidx.compose.ui.graphics.Color.Gray.copy(alpha = 0.3f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityItem(atividade: com.example.analyticai.model.Dashboard.AtividadeResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = atividade.atividade,
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = atividade.categoria,
                style = MaterialTheme.typography.bodySmall,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
        
        Text(
            text = String.format("%.1f", atividade.nota),
            style = MaterialTheme.typography.bodyLarge,
            color = if (atividade.nota >= 7.0f) androidx.compose.ui.graphics.Color(0xFF4CAF50) else androidx.compose.ui.graphics.Color(0xFFFF9800),
            fontWeight = FontWeight.Bold
        )
    }
}
