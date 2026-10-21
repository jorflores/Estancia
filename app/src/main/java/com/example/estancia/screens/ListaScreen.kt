package com.example.estancia.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estancia.components.NinoCard
import com.example.estancia.model.Niño
import com.example.estancia.model.ninosDePrueba

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    ninos: List<Niño>,
    onClick: (Niño) -> Unit,
    onAgregar: () -> Unit,
    error: String? = null    // Mensaje para mostrar arriba de la lista (null = sin error)
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Niños de la estancia") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAgregar) { Text("+ Agregar") }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (error != null) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (ninos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Todavía no hay niños registrados.")
                }
            } else {
                LazyColumn {
                    items(ninos, key = { it.id }) { nino ->
                        NinoCard(nino = nino, onClick = onClick)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaScreenPreview() {
    ListaScreen(ninos = ninosDePrueba(), onClick = {}, onAgregar = {})
}
