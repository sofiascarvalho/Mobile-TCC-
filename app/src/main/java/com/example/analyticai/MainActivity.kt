package com.example.analyticai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.analyticai.screens.ConfirmEmail
import com.example.analyticai.screens.DashboardScreen
import com.example.analyticai.screens.LoginScreen
import com.example.analyticai.screens.RankingScreen
import com.example.analyticai.screens.RecPasswd
import com.example.analyticai.screens.RecursosScreen
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
                val navController = rememberNavController() // NavController único

                val topBarRoutes = listOf(
                    "recursos",
                    "ranking",
                    "dashboard"
                )
                // Rotas que devem exibir a barra inferior
                val bottomBarRoutes = listOf(
                    "profile",
                    "recursos",
                    "ranking",
                    "dashboard"
                )

                Scaffold(
                    topBar = {val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                        startDestination = "login", // tela inicial
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") { LoginScreen(navController) }
                        composable("recPasswd") { RecPasswd(navController) }
                        composable("email") { ConfirmEmail(navController) }
                        composable("profile") { ProfileScreen(navController) }
                        composable("recursos") { RecursosScreen(navController) }
                        composable("ranking") { RankingScreen(navController) }
                        composable("dashboard") { DashboardScreen(navController) }
                    }
                }
            }
        }
    }
}

