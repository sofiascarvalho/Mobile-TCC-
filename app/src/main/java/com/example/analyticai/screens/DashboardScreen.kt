package com.example.analyticai.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun DashboardScreen(navegacao: NavHostController?) {
    // 🍕 Dados da pizza
    val entries = listOf(
        PieEntry(40f, "Maçã"),
        PieEntry(30f, "Banana"),
        PieEntry(20f, "Morango"),
        PieEntry(10f, "Outros")
    )

    val dataSet = PieDataSet(entries, "Frutas favoritas")
    dataSet.colors = listOf(
        android.graphics.Color.RED,
        android.graphics.Color.YELLOW,
        android.graphics.Color.MAGENTA,
        android.graphics.Color.GRAY
    )

    val pieData = PieData(dataSet)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            PieChart(context).apply {
                data = pieData
                description.text = "Preferências"
                animateY(1000)
            }
        }
    )
}
