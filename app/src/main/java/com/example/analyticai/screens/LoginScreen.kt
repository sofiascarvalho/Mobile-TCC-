package com.example.analyticai.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
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

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val rememberMe = remember { mutableStateOf(false) }
    val erroState = remember { mutableStateOf("") }


    // Criar canal de notificação (necessário no Android 8+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "LOGIN_CHANNEL_ID",
                "Canal de Login",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações relacionadas ao login"
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
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
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Matrícula
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Matrícula")
                }
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("0000000000", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffF9FAFB),
                        unfocusedContainerColor = Color(0xffF9FAFB),
                        focusedBorderColor = Color(0xffE1E4E7),
                        unfocusedBorderColor = Color(0xffE1E4E7),
                        focusedLabelColor = Color(0xffC2ACAF),
                        unfocusedLabelColor = Color(0xffC2ACAF)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Senha
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Senha")
                }
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("•••••••••••", fontSize = 14.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffF9FAFB),
                        unfocusedContainerColor = Color(0xffF9FAFB),
                        focusedBorderColor = Color(0xffE1E4E7),
                        unfocusedBorderColor = Color(0xffE1E4E7),
                        focusedLabelColor = Color(0xffC2ACAF),
                        unfocusedLabelColor = Color(0xffC2ACAF)
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
                        Text("Esqueceu a senha?", fontSize = 10.sp, textDecoration = TextDecoration.Underline)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        loginViewModel.login(
                            credencial = emailState.value,
                            senha = passwordState.value,
                            onSuccess = { usuario ->
                                showLoginNotification(context)
                                navegacao?.navigate("dashboard")
                            },
                            onError = {mensagem ->
                                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
                            }
                        ) },
                    enabled = emailState.value.isNotBlank() && passwordState.value.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2))
                ) {
                    Text("Entrar", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}

// Notificação de login
fun showLoginNotification(context: Context) {
    val builder = NotificationCompat.Builder(context, "LOGIN_CHANNEL_ID")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Login realizado")
        .setContentText("Bem-vindo de volta!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
}


@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(null)
}
