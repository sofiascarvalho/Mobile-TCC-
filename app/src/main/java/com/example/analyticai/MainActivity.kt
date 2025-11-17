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
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.screens.DashboardScreen
import com.example.analyticai.screens.LoginScreen
import com.example.analyticai.screens.components.BarraInferior
import com.example.analyticai.screens.components.BarraSuperior
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
    val sharedPrefsManager = remember { SharedPreferencesManager(context) }

    // ---- START DESTINATION CORRIGIDO ----
    val startDestination =
        if (sharedPrefsManager.getCredential() != null)
            "dashboard"
        else
            "login"
    // --------------------------------------

    val bottomBarRoutes = listOf("dashboard", "ranking", "profile")
    val topBarRoutes = listOf("dashboard", "ranking", "profile")

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
                DashboardScreen(navController)
            }

            composable("ranking") {
                RankingScreen(navController)
            }
        }
    }
}
