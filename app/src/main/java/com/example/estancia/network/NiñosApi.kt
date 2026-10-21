package com.example.estancia.network

import com.example.estancia.model.Niño
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/** Las 4 operaciones del CRUD sobre /ninos. */
interface NiñosApi {

    // R: leer la lista completa
    @GET("niños")
    suspend fun obtenerNiños(): List<Niño>

    // C: crear. El niño viaja como JSON en el cuerpo; el servidor regresa el registro con su id.
    @POST("niños")
    suspend fun crearNiño(@Body nino: Niño): Niño

    // U: actualizar. El id va en la URL y los datos nuevos en el cuerpo.
    @PUT("niños/{id}")
    suspend fun actualizarNiño(@Path("id") id: String, @Body nino: Niño): Niño

    // D: borrar. No nos interesa lo que responde, por eso no declara tipo de retorno.
    @DELETE("niños/{id}")
    suspend fun eliminarNiño(@Path("id") id: String)
}
