package com.example.estancia.model

data class Nino(
    val id: String = "",   // Lo asigna quien guarda el registro. Vacío = todavía no se ha guardado.
    val nombre: String,
    val edad: Int,
    val sala: String,      // Una de las SALAS de abajo
    val alergias: String
)

/** Salas de la estancia. El formulario las muestra como opciones. */
val SALAS = listOf("Lactantes", "Maternal", "Preescolar")
