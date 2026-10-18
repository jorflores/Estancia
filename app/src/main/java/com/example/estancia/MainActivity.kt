package com.example.estancia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.estancia.model.Nino
import com.example.estancia.model.ninosDePrueba
import com.example.estancia.navigation.ScreenNames
import com.example.estancia.screens.DetalleScreen
import com.example.estancia.screens.FormularioScreen
import com.example.estancia.screens.ListaScreen
import com.example.estancia.ui.theme.EstanciaTheme

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

    // STARTER: los datos viven en memoria. Si cierras la app, se pierden los cambios.
    val ninos = remember { mutableStateListOf<Nino>().apply { addAll(ninosDePrueba()) } }

    NavHost(navController = nav, startDestination = ScreenNames.Lista.route) {

        composable(ScreenNames.Lista.route) {
            ListaScreen(
                ninos = ninos,
                onClick = { nino -> nav.navigate(ScreenNames.Detalle.createRoute(nino.id)) },
                onAgregar = { nav.navigate(ScreenNames.Nuevo.route) }
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
                    ninos.removeAll { it.id == id }          // LOCAL → con la API será DELETE
                    nav.popBackStack()
                }
            )
        }

        composable(ScreenNames.Nuevo.route) {
            FormularioScreen(
                nino = null,
                onGuardar = { nuevo ->
                    // LOCAL → con la API será POST (y el servidor asigna el id)
                    ninos.add(nuevo.copy(id = System.currentTimeMillis().toString()))
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
                    // LOCAL → con la API será PUT
                    val i = ninos.indexOfFirst { it.id == editado.id }
                    if (i >= 0) ninos[i] = editado
                    nav.popBackStack()
                },
                onCancelar = { nav.popBackStack() }
            )
        }
    }
}
