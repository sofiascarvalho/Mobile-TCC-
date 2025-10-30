package com.example.app.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PresencaChart(
    modifier: Modifier = Modifier,
    // Entradas com valores estáticos, mas podem ser alteradas
    entradas: List<PieEntry> = listOf(
        PieEntry(40f, "Presente"),
        PieEntry(30f, "Ausente"),
        PieEntry(30f, "Justificado")
    )
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        factory = {
            PieChart(context).apply {
                val dataSet = PieDataSet(entradas, "Frequência")
                dataSet.colors = listOf(
                    AndroidColor.parseColor("#B69DF8"),
                    AndroidColor.parseColor("#E5DAFB"),
                    AndroidColor.parseColor("#FFC107")
                )
                dataSet.valueTextSize = 14f
                dataSet.valueTextColor = AndroidColor.BLACK

                data = PieData(dataSet)
                description = Description().apply { text = "" }
                legend.isEnabled = true
                setHoleColor(AndroidColor.TRANSPARENT)
                animateY(800)
                invalidate() // Atualiza o gráfico
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PresencaChartPreview() {
    PresencaChart()
}
