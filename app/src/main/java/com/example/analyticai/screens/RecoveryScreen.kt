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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card (
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ){
                Column (
                    modifier = Modifier.padding(32.dp).fillMaxSize(),
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
                                contentDescription = "Voltar"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Recuperar Senha",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Digite sua matrícula para enviarmos um e-mail de recuperação.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        value = credencial,
                        onValueChange = { credencial = it; error = null },
                        label = { Text("Matrícula") },
                        placeholder = { Text("Sua Matrícula") },
                        isError = error != null,
                        supportingText = { error?.let { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PurplePrimary,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
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
                            containerColor = Color(0xFF673AB7) // Cor roxa do seu tema
                        )
                    ) {
                        if (recoveryState is RecoveryState.Loading) {
                            Text("Enviando...", color = Color.White)
                        } else {
                            Text("Enviar", color = Color.White)
                        }
                    }
                }
            }
        }
    }