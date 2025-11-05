package com.example.app.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.analyticai.ui.theme.BlackLight
import com.example.analyticai.ui.theme.DarkGray
import com.example.analyticai.ui.theme.GrayDarkMedium
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.app.ui.screens.PurplePrimary


//Card informações gerais
@Composable
fun CardInformaçõesGerais(
    title: String,
    matricula: Int,
    dataNasc:String,
    responsavel: String,
    contato: String,
    email: String
    ) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = GrayDarkMedium)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = DarkGray,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Matrícula: $matricula",
                color = BlackLight,
                fontWeight = FontWeight.Normal)
            Text("Data de Nascimento: $dataNasc",
                color = BlackLight,
                fontWeight = FontWeight.Normal)
            Text("Responsável: $responsavel",
                color = BlackLight,
                fontWeight = FontWeight.Normal)
            Text("Contato: $contato",
                color = BlackLight,
                fontWeight = FontWeight.Normal)
            Text(
                "Email: $email",
                color = BlackLight,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// Card de Desempenho com nota
@Composable
fun CardDesempenho(title: String, nota: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = GrayDarkMedium)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Medium)
            Row {
                Text(
                    nota,
                    color = PurplePrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "0.3 Desde o \nÚltimo Semestre"
                )
            }

        }
    }
}

// Card de Atividade ou Avaliação
@Composable
fun CardAtividade(title: String, description: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = GrayDarkMedium)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Medium, color = GrayDarkMedium)
            Text(description, fontWeight = FontWeight.Normal, color = GrayDarkMedium)
        }
    }
}

// Card com gráfico de pizza (frequência)
@Composable
fun CardPieChart(title: String, entries: List<PieEntry>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = GrayDarkMedium)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = GrayDarkMedium)
            Spacer(modifier = Modifier.height(8.dp))
            PresencaChart(entries)
        }
    }
}

@Composable
fun PresencaChart(entries: List<PieEntry>) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            PieChart(context).apply {
                setUsePercentValues(true)
                description.isEnabled = false
                legend.isEnabled = false
                setDrawEntryLabels(false)
                setHoleColor(AndroidColor.TRANSPARENT)
                setTransparentCircleAlpha(0)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        update = { chart ->
            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(
                    AndroidColor.rgb(0, 150, 136),
                    AndroidColor.rgb(255, 193, 7),
                    AndroidColor.rgb(244, 67, 54)
                )
                valueTextSize = 12f
                valueTextColor = AndroidColor.BLACK
            }
            chart.data = PieData(dataSet)
            chart.invalidate()
        }
    )
}
