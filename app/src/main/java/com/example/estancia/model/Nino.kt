package com.example.estancia.model

import kotlinx.serialization.Serializable

@Serializable
data class Nino(
    val id: String = "",   // Lo asigna el servidor. Vacío = todavía no se ha guardado.
    val nombre: String,
    val edad: Int,
    val sala: String,      // Una de las SALAS de abajo
    val alergias: String
)

/** Salas de la estancia. El formulario las muestra como opciones. */
val SALAS = listOf("Lactantes", "Maternal", "Preescolar")
