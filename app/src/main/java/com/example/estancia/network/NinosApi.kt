package com.example.estancia.network

import com.example.estancia.model.Nino
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/** Las 4 operaciones del CRUD sobre /ninos. */
interface NinosApi {

    // R: leer la lista completa
    @GET("ninos")
    suspend fun obtenerNinos(): List<Nino>

    // C: crear. El niño viaja como JSON en el cuerpo; el servidor regresa el registro con su id.
    @POST("ninos")
    suspend fun crearNino(@Body nino: Nino): Nino

    // U: actualizar. El id va en la URL y los datos nuevos en el cuerpo.
    @PUT("ninos/{id}")
    suspend fun actualizarNino(@Path("id") id: String, @Body nino: Nino): Nino

    // D: borrar. No nos interesa lo que responde, por eso no declara tipo de retorno.
    @DELETE("ninos/{id}")
    suspend fun eliminarNino(@Path("id") id: String)
}
