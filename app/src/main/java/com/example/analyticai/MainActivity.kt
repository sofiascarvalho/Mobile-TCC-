package com.example.analyticai

import com.example.analyticai.screens.RankingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.screens.DashboardScreen
import com.example.analyticai.screens.LoginScreen
import com.example.analyticai.screens.ProfileScreen
import com.example.analyticai.screens.RecursosScreen
import com.example.analyticai.screens.RecoveryScreen
import com.example.analyticai.screens.RecoveryConfirmationScreen

import dagger.hilt.android.AndroidEntryPoint

import com.example.analyticai.screens.components.BarraInferior
import com.example.analyticai.screens.components.BarraSuperior
import com.example.analyticai.ui.theme.AnalyticAITheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val sharedPrefsManager = remember { SharedPreferencesManager(context) }
            var themeMode by remember { mutableStateOf(sharedPrefsManager.getThemeMode() ?: "system") }

            DisposableEffect(sharedPrefsManager) {
                val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                    if (key == "theme_mode") {
                        themeMode = sharedPrefsManager.getThemeMode() ?: "system"
                    }
                }
                sharedPrefsManager.registerListener(listener)
                onDispose { sharedPrefsManager.unregisterListener(listener) }
            }

            val darkTheme = when (themeMode) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }

            AnalyticAITheme(darkTheme = darkTheme) {
                AppNavigationContainer()
            }
        }
    }
}

@Composable
fun AppNavigationContainer() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val sharedPrefsManager = remember { SharedPreferencesManager(context) }

    // --- Lógica de Redirecionamento Inicial (Start Destination) ---
    val startDestination = if (sharedPrefsManager.getCredential() != null) {
        // Se há credenciais salvas, direciona para o dashboard correto
        when (sharedPrefsManager.getNivel()?.lowercase()) {
            "aluno" -> "dashboard"
            else -> "login" // Default para aluno se o nível for nulo ou desconhecido
        }
    } else {
        // Se não há credenciais, vai para a tela de Login
        "login"
    }
    // -------------------------------------------------------------
    // ---- START DESTINATION CORRIGIDO ----

    val bottomBarRoutes = listOf("dashboard", "recursos","ranking", "profile")
    val topBarRoutes = listOf("dashboard", "recursos","ranking", "profile")

    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute in topBarRoutes) {
                BarraSuperior(navController)
            }
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute in bottomBarRoutes) {
                BarraInferior(navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("login") {
                LoginScreen(navController)
            }

            // ---- ÚNICO DASHBOARD EXISTENTE ----
            composable("dashboard") {
                DashboardScreen()
            }

            composable ("recursos"){
                RecursosScreen(navController)
            }

            composable("ranking") {
                RankingScreen(navController)
            }

            composable("profile") {
                ProfileScreen(navController)
            }

            composable("recovery") {
                RecoveryScreen(navController)
            }

            composable("recovery_confirmation") {
                RecoveryConfirmationScreen(navController)
            }
        }
    }
}
