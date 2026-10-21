package com.example.estancia.data

import com.example.estancia.model.Niño
import com.example.estancia.network.NiñosApi
import com.example.estancia.network.RetrofitClient

/** Sabe de dónde vienen y a dónde van los datos: al servidor. */
class NiñoRepository(private val api: NiñosApi = RetrofitClient.api) {

    suspend fun obtenerTodos(): List<Niño> = api.obtenerNiños()

    suspend fun crear(niño: Niño): Niño = api.crearNiño(niño)

    suspend fun actualizar(niño: Niño): Niño = api.actualizarNiño(niño.id, niño)

    suspend fun eliminar(id: String) = api.eliminarNiño(id)
}
