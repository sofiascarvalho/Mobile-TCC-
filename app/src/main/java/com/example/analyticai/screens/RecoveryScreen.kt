package com.example.analyticai.screens

import android.R.attr.navigationIcon
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.analyticai.viewmodel.RecoveryState
import com.example.analyticai.viewmodel.RecoveryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryScreen(
    navController: NavController,
    viewModel: RecoveryViewModel = hiltViewModel()
) {
    var credencial by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val recoveryState = viewModel.recoveryState.collectAsState().value

    LaunchedEffect(recoveryState) {
        when (val state = recoveryState) {
            is RecoveryState.Success -> {
                navController.navigate("recovery_confirmation")
                viewModel.resetState()
            }
            is RecoveryState.Error -> {
                error = state.message
                viewModel.resetState()
            }
            else -> {}
        }
    }

    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card (
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ){
            Column (
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Recuperar Senha",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Digite sua matrícula para enviarmos um e-mail de recuperação.",
                    fontSize = 14.sp,
                    color = colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = credencial,
                    onValueChange = { credencial = it; error = null },
                    label = { Text("Matrícula", color = colorScheme.onSurfaceVariant) },
                    placeholder = { Text("Sua Matrícula", color = colorScheme.onSurfaceVariant) },
                    isError = error != null,
                    supportingText = { error?.let { Text(it, color = colorScheme.error) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        cursorColor = colorScheme.primary,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        focusedLabelColor = colorScheme.primary,
                        unfocusedLabelColor = colorScheme.onSurfaceVariant,
                        focusedContainerColor = colorScheme.surfaceVariant,
                        unfocusedContainerColor = colorScheme.surfaceVariant,
                        disabledContainerColor = colorScheme.surfaceVariant,
                        errorContainerColor = colorScheme.surfaceVariant
                    )
                )

                Button(
                    onClick = {
                        val validationError = viewModel.validarCredencial(credencial)
                        if (validationError == null) {
                            viewModel.recuperarSenha(credencial)
                        } else {
                            error = validationError
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = recoveryState !is RecoveryState.Loading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary,
                        disabledContainerColor = colorScheme.primary.copy(alpha = 0.4f)
                    )
                ) {
                    if (recoveryState is RecoveryState.Loading) {
                        Text("Enviando...", color = colorScheme.onPrimary)
                    } else {
                        Text("Enviar", color = colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}