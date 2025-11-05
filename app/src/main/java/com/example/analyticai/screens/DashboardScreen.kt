package com.example.app.ui.screens

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.GrayDarkMedium
import com.example.analyticai.viewmodel.LoginViewModel
import com.example.app.ui.components.CardAtividade
import com.example.app.ui.components.CardDesempenho
import com.example.app.ui.components.CardInformaçõesGerais
import com.example.app.ui.components.CardPieChart
import com.example.app.ui.components.DesempenhoChart
import com.github.mikephil.charting.data.PieEntry

val PurplePrimary = Color(0xFFB69DF8)
val BackgroundColor = Color(0xFFF8F6FB)

@Composable
fun DashboardScreen(navegacao: NavHostController?) {

    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.provideFactory(context)
    )
    val usuario by loginViewModel.usuarioFlow().collectAsState(initial = null)

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
                text = "Dashboard de (Nome do Aluno)",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = GrayDarkMedium
            )
            Spacer(Modifier.height(16.dp))
            CardInformaçõesGerais(
                "Informações gerais",
                45815823,
                "11/00/1010",
                "Nome do responsável",
                "(11) 11111-1111",
                "exemplo.exemplo@gmail.com")
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
            Spacer(Modifier.height(36.dp))
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
            Divider(modifier = Modifier.width(500.dp).height(1.dp))
            Spacer(Modifier.height(24.dp))
            Text(text = "Relatório e Insights por matéria",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = GrayDarkMedium)
            Spacer(Modifier.height(24.dp))
            val atividadesMatematica = listOf(
                "Atividades e Avaliações de Matemática",
                "Prova de Matemática",
                "Exercícios de Reforço"
            )
            atividadesMatematica.forEach { atividade ->
                CardAtividade(atividade, description = "Com excelente compreensão de álgebra e geometria. As notas de prova indicam grande domínio do conteúdo.")
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(null)
}