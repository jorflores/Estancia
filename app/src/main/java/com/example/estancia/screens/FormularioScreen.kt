package com.example.estancia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estancia.model.Nino
import com.example.estancia.model.SALAS

/**
 * Sirve para dar de alta y para editar.
 * - nino == null  → formulario vacío ("Nuevo niño").
 * - nino != null  → formulario con sus datos ("Editar niño").
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    nino: Nino?,
    onGuardar: (Nino) -> Unit,
    onCancelar: () -> Unit
) {
    // Si viene un niño (editar), los campos empiezan con sus datos; si no, vacíos.
    var nombre by rememberSaveable { mutableStateOf(nino?.nombre ?: "") }
    var edadTexto by rememberSaveable { mutableStateOf(nino?.edad?.toString() ?: "") }
    var sala by rememberSaveable { mutableStateOf(nino?.sala ?: SALAS.first()) }
    var alergias by rememberSaveable { mutableStateOf(nino?.alergias ?: "") }

    val edad = edadTexto.toIntOrNull()
    val esValido = nombre.isNotBlank() && edad != null && edad in 0..6

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (nino == null) "Nuevo niño" else "Editar niño") },
                navigationIcon = { TextButton(onClick = onCancelar) { Text("Cancelar") } }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = edadTexto,
                onValueChange = { edadTexto = it.filter(Char::isDigit) },
                label = { Text("Edad (0 a 6 años)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text("Sala", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SALAS.forEach { opcion ->
                    FilterChip(
                        selected = sala == opcion,
                        onClick = { sala = opcion },
                        label = { Text(opcion) }
                    )
                }
            }

            OutlinedTextField(
                value = alergias,
                onValueChange = { alergias = it },
                label = { Text("Alergias") },
                placeholder = { Text("Ninguna") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    onGuardar(
                        Nino(
                            id = nino?.id ?: "",   // Nuevo: id vacío. Editar: conserva su id.
                            nombre = nombre.trim(),
                            edad = edad ?: 0,
                            sala = sala,
                            alergias = alergias.trim().ifBlank { "Ninguna" }
                        )
                    )
                },
                enabled = esValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioScreenPreview() {
    FormularioScreen(nino = null, onGuardar = {}, onCancelar = {})
}
