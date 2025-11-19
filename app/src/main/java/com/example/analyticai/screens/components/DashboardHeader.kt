package com.example.analyticai.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.analyticai.screens.PurplePrimary
import com.example.analyticai.screens.TextDark

@Composable
fun DashboardHeader(
    title: String,
    onActionClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(
            onClick = onActionClick,
            modifier = Modifier
                    .background(
                        PurplePrimary.copy(alpha = 0.1f), 
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Atualizar",
                tint = PurplePrimary
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDashboardHeader(){
    DashboardHeader(title = "")
}