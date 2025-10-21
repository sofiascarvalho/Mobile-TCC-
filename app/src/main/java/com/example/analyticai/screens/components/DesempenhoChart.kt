package com.example.analyticai.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.analyticai.model.Desempenho
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun DesempenhoChart(desempenho: List<Desempenho>) {
    AndroidView(factory = { context ->
        BarChart(context).apply {
            description.isEnabled = false
            legend.isEnabled = false

            val entries = atividades.mapIndexed { index, atividade ->
                BarEntry(index.toFloat(), atividade.nota.toFloat())
            }

            val dataSet = BarDataSet(entries, "Notas").apply {
                color = Color.parseColor("#7D53F3")
                valueTextSize = 12f
            }

            val barData = BarData(dataSet).apply { barWidth = 0.5f }
            data = barData

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(atividades.map { it.atividade })
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }

            axisRight.isEnabled = false
            axisLeft.axisMinimum = 0f
            invalidate()
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height(200.dp))
}



