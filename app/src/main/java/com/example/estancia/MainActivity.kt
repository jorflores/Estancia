package com.example.estancia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.estancia.navigation.ScreenNames
import com.example.estancia.screens.DetalleScreen
import com.example.estancia.screens.FormularioScreen
import com.example.estancia.screens.ListaScreen
import com.example.estancia.ui.theme.EstanciaTheme
import com.example.estancia.viewmodel.NiñosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstanciaTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val nav = rememberNavController()

    // NUEVO: los datos vienen del servidor a través del ViewModel (antes: mutableStateListOf en memoria)
    val vm: NiñosViewModel = viewModel()
    val ninos by vm.ninos.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()

    NavHost(navController = nav, startDestination = ScreenNames.Lista.route) {

        composable(ScreenNames.Lista.route) {
            ListaScreen(
                ninos = ninos,
                onClick = { nino -> nav.navigate(ScreenNames.Detalle.createRoute(nino.id)) },
                onAgregar = { nav.navigate(ScreenNames.Nuevo.route) },
                error = error                                  // NUEVO
            )
        }

        composable(
            route = ScreenNames.Detalle.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetalleScreen(
                nino = ninos.find { it.id == id },
                onRegresar = { nav.popBackStack() },
                onEditar = { nav.navigate(ScreenNames.Editar.createRoute(id)) },
                onEliminar = {
                    vm.eliminar(id)                            // NUEVO: DELETE
                    nav.popBackStack()
                }
            )
        }

        composable(ScreenNames.Nuevo.route) {
            FormularioScreen(
                nino = null,
                onGuardar = { nuevo ->
                    vm.agregar(nuevo)                          // NUEVO: POST (el servidor asigna el id)
                    nav.popBackStack()
                },
                onCancelar = { nav.popBackStack() }
            )
        }

        composable(
            route = ScreenNames.Editar.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            FormularioScreen(
                nino = ninos.find { it.id == id },
                onGuardar = { editado ->
                    vm.actualizar(editado)                     // NUEVO: PUT
                    nav.popBackStack()
                },
                onCancelar = { nav.popBackStack() }
            )
        }
    }
}
