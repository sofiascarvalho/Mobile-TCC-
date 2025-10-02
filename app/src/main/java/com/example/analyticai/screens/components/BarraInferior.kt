package com.example.analyticai.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.analyticai.R
import com.example.analyticai.ui.theme.BackgroundLightPink

@Composable
fun BarraInferior(controleNavegacao: NavHostController?) {

    NavigationBar(
        containerColor = BackgroundLightPink,
        contentColor = Color(0xff363636)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                controleNavegacao!!.navigate("conteudo")
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.dashboard_icon),
                    contentDescription = "Dashboard",
                    tint = Color(0xff363636),
                    modifier = Modifier.size(25.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.recursos_icon),
                    contentDescription = "Recursos",
                    tint = Color(0xff363636),
                    modifier = Modifier.size(25.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                controleNavegacao!!.navigate("cadastro")
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ranking_icon),
                    contentDescription = "Novo cliente",
                    tint = Color(0xff363636),
                    modifier = Modifier.size(25.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                controleNavegacao!!.navigate("cadastro")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = Color(0xff363636),
                    modifier = Modifier.size(25.dp)
                )
            }
        )
    }
}

@Preview
@Composable
private fun BarraInferiorPreview(){
    BarraInferior(null)
}