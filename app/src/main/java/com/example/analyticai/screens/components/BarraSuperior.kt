package com.example.analyticai.screens.components

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.ui.theme.DarkGray

@Composable
fun BarraSuperior(navController: NavHostController?) {

    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    var usuario by remember { mutableStateOf(sharedPrefs.getUsuario()) }

    DisposableEffect(sharedPrefs) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            usuario = sharedPrefs.getUsuario()
        }
        sharedPrefs.registerListener(listener)
        onDispose { sharedPrefs.unregisterListener(listener) }
    }

    val userName = usuario?.nome ?: "Usuário"
    val userTurma = usuario?.turma?.turma ?: "Turma não informada"

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp)
        ){
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .padding(start = 10.dp, end = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text("$userName", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = DarkGray, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    Text("$userTurma", fontSize = 14.sp, color = DarkGray, fontWeight = FontWeight.Light)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Divider(modifier = Modifier.height(1.dp).fillMaxWidth())
        }
    }