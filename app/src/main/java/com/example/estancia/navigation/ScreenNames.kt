package com.example.estancia.navigation

sealed class ScreenNames(val route: String) {
    data object Lista : ScreenNames("lista")

    data object Detalle : ScreenNames("detalle/{id}") {
        fun createRoute(id: String) = "detalle/$id"
    }

    data object Nuevo : ScreenNames("nuevo")

    data object Editar : ScreenNames("editar/{id}") {
        fun createRoute(id: String) = "editar/$id"
    }
}
