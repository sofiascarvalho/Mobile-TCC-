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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.analyticai.R

@Composable
fun BarraInferior(navController: NavHostController) {
    val colorScheme = MaterialTheme.colorScheme

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorScheme.surface,
        contentColor = colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(0.dp),
                clip = false,
                ambientColor = colorScheme.onSurface.copy(alpha = 0.08f),
                spotColor = colorScheme.onSurface.copy(alpha = 0.08f)
            ),
        tonalElevation = NavigationBarDefaults.Elevation
    ) {
        NavigationItem(
            route = "dashboard",
            currentRoute = currentRoute,
            navController = navController,
            label = "Dashboard",
            iconPainter = painterResource(R.drawable.dashboard_icon),
            isVectorIcon = false
        )

        NavigationItem(
            route = "recursos",
            currentRoute = currentRoute,
            navController = navController,
            label = "Recursos",
            iconPainter = painterResource(R.drawable.recursos_icon),
            isVectorIcon = false
        )

        NavigationItem(
            route = "ranking",
            currentRoute = currentRoute,
            navController = navController,
            label = "Ranking",
            iconPainter = painterResource(R.drawable.ranking_icon),
            isVectorIcon = false
        )

        NavigationItem(
            route = "profile",
            currentRoute = currentRoute,
            navController = navController,
            label = "Perfil",
            iconVector = Icons.Default.Person,
            isVectorIcon = true
        )
    }
}

@Composable
private fun RowScope.NavigationItem(
    route: String,
    currentRoute: String?,
    navController: NavHostController,
    label: String,
    iconPainter: Painter? = null,
    iconVector: ImageVector? = null,
    isVectorIcon: Boolean
) {
    val colorScheme = MaterialTheme.colorScheme

    val selected = currentRoute == route
    val selectedColor = colorScheme.primary
    val unselectedIconColor = colorScheme.onSurfaceVariant
    val unselectedTextColor = colorScheme.onSurfaceVariant
    val iconColor = if (selected) colorScheme.onPrimary else unselectedIconColor

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
            Box(
                modifier = Modifier
                    .background(
                        color = if (selected) selectedColor else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (isVectorIcon && iconVector != null) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = label,
                            tint = iconColor,
                            modifier = Modifier.size(25.dp)
                        )
                    } else if (!isVectorIcon && iconPainter != null) {
                        Icon(
                            painter = iconPainter,
                            contentDescription = label,
                            tint = iconColor,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        },
        label = {
            Text(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (selected) colorScheme.onSurface else unselectedTextColor
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = colorScheme.onPrimary,
            selectedTextColor = colorScheme.onSurface,
            indicatorColor = Color.Transparent,
            unselectedIconColor = unselectedIconColor,
            unselectedTextColor = unselectedTextColor
        )
    )
}

