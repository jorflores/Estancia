package com.example.estancia.data

import com.example.estancia.model.Nino
import com.example.estancia.network.NinosApi
import com.example.estancia.network.RetrofitClient

/** Sabe de dónde vienen y a dónde van los datos: al servidor. */
class NinoRepository(private val api: NinosApi = RetrofitClient.api) {

    suspend fun obtenerTodos(): List<Nino> = api.obtenerNinos()

    suspend fun crear(nino: Nino): Nino = api.crearNino(nino)

    suspend fun actualizar(nino: Nino): Nino = api.actualizarNino(nino.id, nino)

    suspend fun eliminar(id: String) = api.eliminarNino(id)
}
