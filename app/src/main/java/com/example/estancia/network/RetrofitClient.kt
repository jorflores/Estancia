package com.example.estancia.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {

    // ¿A dónde apunta la app?
    //  - Emulador: 10.0.2.2 es la computadora donde corre el emulador (su "localhost").
    //  - Celular físico: la IP de tu computadora en el Wi-Fi, por ejemplo "http://192.168.1.50:3000/".
    // 3000 es el puerto donde escucha json-server.
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val json = Json { ignoreUnknownKeys = true }

    val api: NinosApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(NinosApi::class.java)
}
