package com.example.analyticai.screens.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.analyticai.model.response.RecursoResponse
import com.example.analyticai.ui.theme.BlackLight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.RectangleShape
import com.example.analyticai.ui.theme.PurplePrimary
import com.example.analyticai.ui.theme.getDisciplinaColor


@Composable
fun RecursoCard(
    recurso: RecursoResponse,
    onBaixarClick: (RecursoResponse) -> Unit
) {
    val corDisciplina = getDisciplinaColor(recurso.disciplina)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xfff5f5f5)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Card (
                modifier = Modifier
                    .width(5.dp)
                    .height(170.dp),
                colors = CardDefaults.cardColors(
                    containerColor = corDisciplina
                ),
                shape = RectangleShape
            ){}
            // Conteúdo principal
            Box(modifier = Modifier
                .weight(1f)
                .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = recurso.titulo,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = recurso.descricao,
                        fontSize = 14.sp,
                        color = BlackLight,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { onBaixarClick(recurso) },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                    ) {
                        Text(text = "Baixar PDF")
                    }
                }

                // Bolinha superior direita
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(corDisciplina, shape = CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}


@Composable
fun ConfirmarDownload(
    recurso: RecursoResponse?,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
    ) {
    if (recurso != null){
        AlertDialog(
            onDismissRequest = {onCancelar()},
            title = {
                Text(
                    text = "Confirmar Download",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            text = {
                Text(
                    text = "Você está prestes a baixar o conteúdo ${recurso.titulo}."
                )
            },
            confirmButton = {
                Button(
                    onClick = onConfirmar
                ) {
                    Text(
                        text = "Baixar"
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCancelar
                ) {
                    Text(
                        text = "Cancelar"
                    )
                }
            }
        )
    }
}