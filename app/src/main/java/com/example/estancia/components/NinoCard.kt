package com.example.estancia.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estancia.model.Nino
import com.example.estancia.model.ninosDePrueba

@Composable
fun NinoCard(
    nino: Nino,
    onClick: (Nino) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick(nino) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con la inicial del nombre
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nino.nombre.take(1).uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(nino.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${nino.edad} años · ${nino.sala}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (tieneAlergias(nino)) {
                    Text(
                        text = "Alergias: ${nino.alergias}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

/** true si el campo de alergias tiene algo distinto de "Ninguna". */
fun tieneAlergias(nino: Nino): Boolean =
    nino.alergias.isNotBlank() && !nino.alergias.equals("Ninguna", ignoreCase = true)

@Preview(showBackground = true)
@Composable
fun NinoCardPreview() {
    NinoCard(nino = ninosDePrueba()[1])
}
