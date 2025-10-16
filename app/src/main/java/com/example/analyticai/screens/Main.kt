package com.example.analyticai.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun WeatherScreen() {
    val temperatures = listOf(20f, 22f, 25f, 23f, 24f, 26f, 28f)

    WeatherChart(
        modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
        dataPoints = temperatures
    )
}

@Composable
fun WeatherChart(
    modifier: Modifier = Modifier,
    dataPoints: List<Float>
) {

    //transformar dados em entries dp MPAndroidChart
    val entries = dataPoints.mapIndexed { index, value ->
        Entry(index.toFloat(), value)
    }

    //dataset
    val dataSet = LineDataSet(entries, "Temperatura (ºC)").apply {
        color = android.graphics.Color.BLUE
        valueTextColor = android.graphics.Color.BLACK
        lineWidth = 2f
        circleRadius = 4f
        setCircleColor(android.graphics.Color.RED)
        setDrawValues(true)
    }

    val lineData = LineData(dataSet)

    // AndroidView recebe um factory que cria a View tradicional
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // Aqui você cria a View do MPAndroidChart
            LineChart(context).apply {
                this.data = lineData
                description.isEnabled = false
                legend.isEnabled = true
                animateX(1000)
            }
        },
        update = { chart ->
            // Aqui você atualiza os dados do gráfico
            chart.data = lineData
            chart.invalidate()
        }
    )
}

