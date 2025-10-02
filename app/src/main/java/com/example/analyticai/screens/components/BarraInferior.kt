package com.example.analyticai.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.analyticai.R

@Composable
fun BarraInferior(navController: NavHostController) {
    // cor do background da barra (use seu BackgroundLightPink se preferir)
    val containerColor = Color(0xFFFCF5FF)
    val iconColor = Color(0xFF363636)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = containerColor,
        contentColor = iconColor
    ) {
        NavigationItem(
            route = "dashboard",
            currentRoute = currentRoute,
            navController = navController,
            label = "Dashboard",
            iconComposable = {
                Icon(
                    painter = painterResource(R.drawable.dashboard_icon),
                    contentDescription = "Dashboard",
                    tint = iconColor,
                    modifier = Modifier.size(25.dp)
                )
            }
        )

        NavigationItem(
            route = "recursos",
            currentRoute = currentRoute,
            navController = navController,
            label = "Recursos",
            iconComposable = {
                Icon(
                    painter = painterResource(R.drawable.recursos_icon),
                    contentDescription = "Recursos",
                    tint = iconColor,
                    modifier = Modifier.size(25.dp)
                )
            }
        )

        NavigationItem(
            route = "ranking",
            currentRoute = currentRoute,
            navController = navController,
            label = "Ranking",
            iconComposable = {
                Icon(
                    painter = painterResource(R.drawable.ranking_icon),
                    contentDescription = "Ranking",
                    tint = iconColor,
                    modifier = Modifier.size(25.dp)
                )
            }
        )

        NavigationItem(
            route = "profile",
            currentRoute = currentRoute,
            navController = navController,
            label = "profile",
            iconComposable = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "profile",
                    tint = iconColor,
                    modifier = Modifier.size(25.dp)
                )
            }
        )
    }
}

@Composable
private fun RowScope.NavigationItem(
    route: String,
    currentRoute: String?,
    navController: NavHostController,
    label: String,
    iconComposable: @Composable () -> Unit
) {
    val selected = currentRoute == route

    NavigationBarItem(
        selected = selected,
        onClick = {
            if (currentRoute != route) {
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                iconComposable()
                Spacer(modifier = Modifier.height(4.dp))
                if (selected) {
                    // barrinha roxa pequena
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(3.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFF7D53F3))
                    )
                } else {
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        },
        label = {
            Text(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF363636),
            selectedTextColor = Color(0xFF363636),
            indicatorColor = Color(0xFFD6C6FF), // fundo lilás quando ativo
            unselectedIconColor = Color(0xFF8A8A8A),
            unselectedTextColor = Color(0xFF8A8A8A)
        )
    )
}

