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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.analyticai.screens.HomeScreen
import com.example.analyticai.screens.LoginScreen
import com.example.analyticai.ui.theme.AnalyticAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnalyticAITheme {
                var navegacao= rememberNavController()
                NavHost(
                    navController=navegacao,
                    startDestination = "home" //nome de associacao a tela
                ){
                    //quando a rota home for chamada,vamos para a tela criada
                    composable(route = "home"){ HomeScreen(navegacao) }
                    composable(route = "login"){ LoginScreen(navegacao) }
                }
                //HomeScreen()
            }
        }
    }
}
