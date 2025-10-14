package com.example.analyticai.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()

    val matriculaState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val rememberMe = remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val isLoading by loginViewModel.isLoading.collectAsState(initial = false)

    // Criar canal de notificação (Android 8+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "LOGIN_CHANNEL_ID",
                "Canal de Login",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Notificações relacionadas ao login" }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Acesse sua conta para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Matrícula
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) { Text("Matrícula") }

                OutlinedTextField(
                    value = matriculaState.value,
                    onValueChange = { matriculaState.value = it },
                    label = { Text("0000000000", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Senha
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) { Text("Senha") }

                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("•••••••••••", fontSize = 14.sp) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Lembrar de mim + Esqueceu a senha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe.value,
                            onCheckedChange = { rememberMe.value = it }
                        )
                        Text("Lembrar de mim", fontSize = 14.sp)
                    }
                    TextButton(onClick = { navegacao?.navigate("recPasswd") }) {
                        Text(
                            "Esqueceu a senha?",
                            fontSize = 10.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão de login com indicador de carregamento
                Button(
                    onClick = {
                        if (matriculaState.value.isBlank() || passwordState.value.isBlank()) return@Button
                        if (matriculaState.value.length < 5) {
                            Toast.makeText(context, "Matrícula inválida", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (passwordState.value.length < 4) {
                            Toast.makeText(context, "Senha muito curta", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        loginViewModel.login(
                            credencial = matriculaState.value,
                            senha = passwordState.value,
                            onSuccess = {
                                showLoginNotification(context)
                                navegacao?.navigate("dashboard")
                            },
                            onError = { mensagem ->
                                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    enabled = !isLoading && matriculaState.value.isNotBlank() && passwordState.value.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Entrar", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

// 🔔 Exibe notificação de login com sucesso
fun showLoginNotification(context: Context) {
    val builder = NotificationCompat.Builder(context, "LOGIN_CHANNEL_ID")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Login realizado")
        .setContentText("Bem-vindo de volta!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(null)
}
