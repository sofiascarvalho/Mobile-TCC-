package com.example.app.ui.screens

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.GrayDarkMedium
import com.example.app.ui.components.CardAtividade
import com.example.app.ui.components.CardDesempenho
import com.example.app.ui.components.CardPieChart
import com.example.app.ui.components.DesempenhoChart
import com.github.mikephil.charting.data.PieEntry

val PurplePrimary = Color(0xFFB69DF8)
val BackgroundColor = Color(0xFFF8F6FB)

@Composable
fun DashboardScreen(navegacao: NavHostController?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Seção 1: Desempenho do Aluno
        item {
            Text(
                text = "Desempenho do Aluno",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = GrayDarkMedium
            )
            Spacer(Modifier.height(16.dp))
            CardDesempenho("Desempenho em Biologia", "9.8")
            Spacer(Modifier.height(16.dp))
            CardPieChart(
                title = "Frequência de Presença",
                entries = listOf(
                    PieEntry(90f, "Presenças"),
                    PieEntry(10f, "Faltas")
                )
            )
        }

        // Seção 2: Desempenho em Matemática
        item {
            Text(
                text = "Desempenho em Matemática",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = GrayDarkMedium
            )
            Spacer(Modifier.height(16.dp))
            val notasFicticias = listOf(8f, 6f, 9f, 7f)
            DesempenhoChart(notas = notasFicticias)
            Spacer(Modifier.height(24.dp))
            val atividadesMatematica = listOf(
                "Atividades e Avaliações de Matemática",
                "Prova de Matemática",
                "Exercícios de Reforço"
            )
            atividadesMatematica.forEach { atividade ->
                CardAtividade(atividade)
            }
        }

        // Seção 3: Desempenho Individual
        item {
            Text(
                text = "Desempenho Individual",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = GrayDarkMedium
            )
            Spacer(Modifier.height(16.dp))
            val atividadesIndividuais = listOf(
                "Avaliação de Matemática",
                "Prova de História",
                "Questionário de Biologia",
                "Atividades de Redação"
            )
            atividadesIndividuais.forEach { atividade ->
                CardAtividade(atividade)
            }

            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(4.dp)
                            .background(
                                if (it == 3) PurplePrimary else Color.LightGray,
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            }
        }
    }
}

