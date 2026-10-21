package com.example.estancia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estancia.model.Niño
import com.example.estancia.model.ninosDePrueba

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    nino: Niño?,
    onRegresar: () -> Unit,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    var confirmarBorrado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = { TextButton(onClick = onRegresar) { Text("Regresar") } }
            )
        }
    ) { innerPadding ->
        if (nino == null) {
            Box(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontró el registro.")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(nino.nombre, style = MaterialTheme.typography.headlineMedium)
            HorizontalDivider()
            Dato(etiqueta = "Edad", valor = "${nino.edad} años")
            Dato(etiqueta = "Sala", valor = nino.sala)
            Dato(etiqueta = "Alergias", valor = nino.alergias)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onEditar, modifier = Modifier.weight(1f)) {
                    Text("Editar")
                }
                OutlinedButton(
                    onClick = { confirmarBorrado = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }
    }

    // Diálogo para confirmar antes de borrar
    if (confirmarBorrado && nino != null) {
        AlertDialog(
            onDismissRequest = { confirmarBorrado = false },
            title = { Text("¿Eliminar a ${nino.nombre}?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarBorrado = false
                    onEliminar()
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { confirmarBorrado = false }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun Dato(etiqueta: String, valor: String) {
    Column {
        Text(etiqueta, style = MaterialTheme.typography.labelMedium)
        Text(valor, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleScreenPreview() {
    DetalleScreen(nino = ninosDePrueba()[1], onRegresar = {}, onEditar = {}, onEliminar = {})
}
