package com.example.estancia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estancia.data.NiñoRepository
import com.example.estancia.model.Niño
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NiñosViewModel(
    private val repositorio: NiñoRepository = NiñoRepository()
) : ViewModel() {

    private val _ninos = MutableStateFlow<List<Niño>>(emptyList())
    val ninos: StateFlow<List<Niño>> = _ninos.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        ejecutar { }   // Ningún cambio: solo vuelve a pedir la lista
    }

    fun agregar(nino: Niño) {
        ejecutar { repositorio.crear(nino) }        // POST
    }

    fun actualizar(nino: Niño) {
        ejecutar { repositorio.actualizar(nino) }   // PUT
    }

    fun eliminar(id: String) {
        ejecutar { repositorio.eliminar(id) }       // DELETE
    }

    /**
     * Las 4 operaciones siguen el mismo patrón:
     * 1. hacer el cambio en el servidor,
     * 2. volver a pedir la lista (GET) para mostrar lo que de verdad quedó guardado.
     */
    private fun ejecutar(cambio: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                cambio()
                _ninos.value = repositorio.obtenerTodos()
                _error.value = null
            } catch (e: IOException) {
                _error.value = "No hay conexión con el servidor. ¿Está corriendo json-server?"
            } catch (e: HttpException) {
                _error.value = "El servidor respondió con error ${e.code()}."
            }
        }
    }
}
