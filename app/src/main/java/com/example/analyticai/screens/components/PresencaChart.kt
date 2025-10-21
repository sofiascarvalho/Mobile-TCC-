package com.example.analyticai.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PresencaChart(presente: Int, ausente: Int) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            description.isEnabled = false
            isRotationEnabled = false
            legend.isEnabled = false

            val entries = listOf(
                PieEntry(presenca.toFloat(), "Presença"),
                PieEntry(falta.toFloat(), "Falta")
            )

            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(Color.parseColor("#DEDCF8"), Color.parseColor("#7D53F3")))
                valueTextColor = Color.Black
                valueTextSize = 12f
            }

            data = PieData(dataSet)
            invalidate()
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height(200.dp))
}


