package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Dashboard.DesempenhoResponse
import com.example.analyticai.screens.components.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.analyticai.viewmodel.FiltrosViewModel

// ... imports ...

import com.example.analyticai.viewmodel.DesempenhoViewModel
import androidx.hilt.navigation.compose.hiltViewModel // Adicionei este para garantir

val PurplePrimary = Color(0xFF673AB7)
val BackgroundColor = Color(0xFFF8F6FB)
val TextDark = Color(0xFF3C3C3C)
val TextGray = Color(0xFF6F6F6F)

@Composable
fun DashboardScreen(
    navController: NavController? = null,
    // Alterei para usar hiltViewModel() como default, pois o DesempenhoViewModel provavelmente
    // usa dependências do Hilt.
    viewModel: DesempenhoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuarioSalvo = sharedPrefs.getUsuario()

    // 1. DADOS DE USUÁRIO (MOVIDOS PARA O INÍCIO DO COMPOSABLE)
    val nomeUsuario = usuarioSalvo?.nome ?: "Usuário"
    val userNivel = usuarioSalvo?.nivel_usuario ?: "aluno"
    val userCredential = usuarioSalvo?.credencial ?: "00000000"
    val turma = usuarioSalvo?.turma?.turma ?: "—"
    val responsavel = if (userNivel.lowercase() == "aluno") "Nome do Responsável" else "—"
    val dataNascimento = usuarioSalvo?.data_nascimento ?: "00/00/0000"
    val telefone = usuarioSalvo?.telefone ?: "(00) 00000-0000"
    val email = usuarioSalvo?.email ?: "exemplo@email.com"

    // 2. DADOS MOCKADOS (MOVIDOS PARA O INÍCIO DO COMPOSABLE)
    val performanceScore = 9.8
    val scoreChange = 0.3
    val presencePercentage = 90f
    val mathPerformanceData = listOf(
        BarData("Atividade", 8.0f),
        BarData("Prova", 9.2f),
        BarData("Seminário", 7.3f),
        BarData("Prova", 10.0f)
    )

    // Filtros selecionados
    var disciplinaSelecionada by remember { mutableStateOf<String?>(null) }
    var periodoSelecionado by remember { mutableStateOf<String?>(null) }

    // ... O LaunchedEffect está correto ...

    LaunchedEffect(usuarioSalvo, disciplinaSelecionada, periodoSelecionado) {
        usuarioSalvo?.id_usuario?.let { idAluno ->
            // Mapear IDs a partir do desempenho atual (ou null se não houver filtro)
            val dashboardAtual = viewModel.desempenho
            val idMateria = dashboardAtual?.desempenho
                ?.firstOrNull { it.materia.materia == disciplinaSelecionada }
                ?.materia?.materia_id

            val idSemestre = periodoSelecionado?.let { selecionado ->
                dashboardAtual?.desempenho
                    ?.firstOrNull { it.semestre == selecionado }
                    ?.id_semestre
            }

            viewModel.loadPerformance(
                idAluno = idAluno,
                idMateria = idMateria,
                idSemestre = idSemestre
            )
        }
    }

    val dashboard = viewModel.desempenho
    val loading = viewModel.isLoading

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // 3. REMOVIDO: O 'val nomeUsuario' aqui estava duplicado.

    // Filtros dinâmicos
    val disciplinasDisponiveis = dashboard?.desempenho
        ?.map { it.materia.materia }
        ?.distinct()
        ?.toMutableList()
        ?: mutableListOf()
    disciplinasDisponiveis.add(0, "Todas as disciplinas")

    val periodosDisponiveis = dashboard?.desempenho
        ?.map { it.semestre }
        ?.distinct()
        ?.toMutableList()
        ?: mutableListOf()
    periodosDisponiveis.sort()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Dashboard de $nomeUsuario",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        // --- INÍCIO DA CORREÇÃO DE INJEÇÃO ---

        // Cria o ViewModel de filtros (Hilt ViewModel)
        val filtrosViewModel: FiltrosViewModel = hiltViewModel()

        // Chama o composable de filtros passando o viewModel corretamente
        FiltrosVerticais(viewModel = filtrosViewModel)

        // --- FIM DA CORREÇÃO DE INJEÇÃO ---

        // Card Informações Gerais com dados reais
        DashboardCard(title = "Informações Gerais") {
            InfoLine("Nome:", nomeUsuario) // Corrigido para nomeUsuario
            InfoLine("Matrícula:", userCredential)
            InfoLine("Nascimento:", dataNascimento)
            if (userNivel.equals("aluno", ignoreCase = true)) {
                InfoLine("Turma:", turma)
                InfoLine("Responsável:", responsavel)
            }
            InfoLine("Contato:", telefone)
            InfoLine("E-mail:", email)
            InfoLine("Nível:", userNivel)
        }

        // 4. ✅ FILTROS MANUAIS (MOVIDOS PARA O LOCAL CORRETO)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownFiltro(
                label = "Disciplina:",
                opcoes = disciplinasDisponiveis,
                selecionado = disciplinaSelecionada ?: "Todas as disciplinas",
                onSelecionar = {
                    val it = null
                    disciplinaSelecionada = it.takeIf { it != "Todas as disciplinas" }
                }
            )
            DropdownFiltro(
                label = "Período:",
                opcoes = periodosDisponiveis,
                selecionado = periodoSelecionado ?: periodosDisponiveis.firstOrNull().orEmpty(),
                onSelecionar = {
                    val it = null
                    periodoSelecionado = it
                }
            )
        }


        // Conteúdo
        dashboard?.desempenho
            ?.filter { item ->
                (disciplinaSelecionada == null || item.materia.materia == disciplinaSelecionada)
                        && (periodoSelecionado == null || item.semestre == periodoSelecionado)
            }
            ?.forEach { item ->

                // --- KPI DE DESEMPENHO ---
                val mediaScore = item.media.toFloatOrNull() ?: 0f
                PerformanceKpiCard(
                    score = mediaScore.toDouble(),
                    change = 0.0,
                    modifier = Modifier.fillMaxWidth()
                )

                // --- KPI DE FREQUÊNCIA ---
                val frequenciaFloat = item.frequencia.porcentagem_frequencia
                    .replace("%", "")
                    .toFloatOrNull() ?: 0f
                FrequencyKpiCard(
                    presentPercentage = frequenciaFloat,
                    modifier = Modifier.fillMaxWidth()
                )

                // --- GRÁFICO DE DESEMPENHO POR ATIVIDADE ---
                val barData = item.atividades.map {
                    BarData(label = it.categoria, value = it.nota)
                }
                SubjectPerformanceChartCard(
                    subject = item.materia.materia,
                    data = barData
                )

                // --- RELATÓRIOS ---
                Text(
                    text = "Relatórios para Download",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark,
                    modifier = Modifier.padding(top = 8.dp)
                )

//                // Se houver campo de relatórios no backend, mapear dinamicamente:
//                item.relatorios?.forEach { rel ->
//                    DownloadCardRefined(rel.titulo, rel.dataGeracao)
//                }
            } ?: Text("Nenhum dado encontrado.")

        // 5. ✅ FECHAMENTO DA COLUNA (MOVIDO PARA O FINAL)
    }
}

@Composable
fun InfoLine(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = TextGray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )
    }
}

@Composable
fun DropdownFiltro(
    label: String,
    opcoes: List<String>,
    selecionado: String,
    onSelecionar: (String?) -> Unit // Mudado para receber a string selecionada (ou null)
) {
    var expanded by remember { mutableStateOf(false) }

    // O filtro ocupa metade da largura, com um pequeno padding
    Box(modifier = Modifier.width(IntrinsicSize.Max)) {
        OutlinedTextField(
            value = selecionado,
            onValueChange = { /* Não reagimos a digitação aqui */ },
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier
                .width(170.dp) // Define uma largura para o campo
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(170.dp) // Alinha a largura do menu com o TextField
        ) {
            opcoes.forEach { opcao ->
                DropdownMenuItem(
                    text = { Text(opcao) },
                    onClick = {
                        expanded = false
                        // Passa null se a opção for "Todas as disciplinas", caso contrário passa o valor.
                        val valorParaPassar = opcao.takeIf { it != "Todas as disciplinas" }
                        onSelecionar(valorParaPassar)
                    }
                )
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PurplePrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
fun DashboardPreview() {
    // 6. ✅ CORREÇÃO FINAL: O Preview original estava faltando as chaves
    DashboardScreen(navController = rememberNavController())
}