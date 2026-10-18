package com.example.estancia.model

/** Datos fijos para el starter y para los @Preview. La app real los traerá del servidor. */
fun ninosDePrueba(): List<Nino> = listOf(
    Nino(id = "1", nombre = "Sofía Ramírez", edad = 3, sala = "Maternal", alergias = "Ninguna"),
    Nino(id = "2", nombre = "Mateo González", edad = 1, sala = "Lactantes", alergias = "Lácteos"),
    Nino(id = "3", nombre = "Valentina López", edad = 5, sala = "Preescolar", alergias = "Nuez"),
)
