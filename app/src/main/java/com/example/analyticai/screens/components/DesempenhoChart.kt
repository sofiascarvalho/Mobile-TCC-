package com.example.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import android.graphics.Color as AndroidColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun DesempenhoChart(
    notas: List<Float>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Transformando a lista de notas em BarEntry
    val entries = notas.mapIndexed { index, nota ->
        BarEntry((index + 1).toFloat(), nota)
    }

    AndroidView(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        factory = {
            BarChart(context).apply {
                val dataSet = BarDataSet(entries, "Notas")
                dataSet.color = AndroidColor.parseColor("#B69DF8")
                dataSet.valueTextSize = 12f
                dataSet.valueTextColor = AndroidColor.BLACK

                this.data = BarData(dataSet)

                // Configurações do gráfico
                this.description = Description().apply { text = "" }
                this.legend.isEnabled = false
                this.axisLeft.axisMinimum = 0f
                this.axisRight.isEnabled = false
                this.xAxis.setDrawGridLines(false)
                this.animateY(1000)
            }
        }
    )
}
