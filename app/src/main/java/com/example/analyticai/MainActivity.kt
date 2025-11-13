package com.example.analyticai

import RankingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.analyticai.data.SharedPreferencesManager // Corrigido
import com.example.analyticai.screens.ConfirmEmail
import com.example.analyticai.screens.LoginScreen
import com.example.analyticai.screens.RecPasswd
// ⚠️ IMPORT CORRIGIDO: Assumindo que DashboardScreen está em com.example.analyticai.screens
//import com.example.analyticai.screens.DashboardScreen // <--- AGORA REFERENCIA O PACOTE CORRETO

import com.example.analyticai.screens.components.BarraInferior
import com.example.analyticai.screens.components.BarraSuperior
import com.example.analyticai.screens.components.ProfileScreen
import com.example.analyticai.ui.theme.AnalyticAITheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnalyticAITheme {
                AppNavigationContainer()
            }
        }
    }
}

@Composable
fun AppNavigationContainer() {
    val context = LocalContext.current
    val navController = rememberNavController()
    // Instância do Manager para checar o estado da sessão
    val sharedPrefsManager = remember { SharedPreferencesManager(context) }

    // --- Lógica de Redirecionamento Inicial (Start Destination) ---
    val startDestination = if (sharedPrefsManager.getCredential() != null) {
        // Se há credenciais salvas, direciona para o dashboard correto
        when (sharedPrefsManager.getNivel()?.lowercase()) {
            "professor" -> "dashboardProfessor"
            "gestão" -> "dashboardGestao"
            else -> "dashboardAluno" // Default para aluno se o nível for nulo ou desconhecido
        }
    } else {
        // Se não há credenciais, vai para a tela de Login
        "ranking"
    }
    // -------------------------------------------------------------

    // Rotas que devem exibir a barra inferior (Dashboard, Recursos, Ranking, Perfil)
    val bottomBarRoutes = listOf(
        "dashboardAluno", "dashboardProfessor", "dashboardGestao", // Todos os dashboards
        "recursos",
        "ranking",
        "profile"
    )
    val topBarRoutes = listOf(
        "dashboardAluno", "dashboardProfessor", "dashboardGestao", // Todos os dashboards
        "recursos",
        "ranking",
        "profile"
    )

    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Mostra a Barra Superior apenas nas rotas principais
            if (currentRoute in topBarRoutes) {
                // Supondo que BarraSuperior seja um TopAppBar ou componente similar
                BarraSuperior(navController)
            }
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            // Mostra a barra inferior apenas se a rota atual for de dashboard/funcionalidade principal
            if (currentRoute in bottomBarRoutes) {
                BarraInferior(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination, // Usa o destino determinado pela checagem de sessão
            modifier = Modifier.padding(innerPadding)
        ) {
            // Rotas de Autenticação/Recuperação (sem Barra Inferior)
            composable("login") { LoginScreen(navController) }
            composable("recPasswd") { RecPasswd(navController) }
            composable("email") { ConfirmEmail(navController) }

            // Rotas de Dashboards (Com Barra Inferior)
            // ⚠️ Aqui você precisa mapear os 3 tipos de dashboard para o Composable correto
            //composable("dashboard") { DashboardScreen(navController) }


            // Outras Rotas Principais (Com Barra Inferior)
            composable("profile") { ProfileScreen(navController) }
            // composable("recursos") { RecursosScreen(navController) }
             composable("ranking") { RankingScreen(navController) }
        }
    }
}