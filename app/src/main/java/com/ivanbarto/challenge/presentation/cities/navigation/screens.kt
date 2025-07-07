package com.ivanbarto.challenge.presentation.cities.navigation

import androidx.navigation.NavController

sealed class CitiesScreens(
    val route: String
) {
    data object MainScreen : CitiesScreens(route = "main")
    data object CityDetails : CitiesScreens(route = "cityDetails")
}

fun CitiesScreens.navigate(navController: NavController, argument: String? = null) {
    when(this) {
        CitiesScreens.CityDetails -> navController.navigate("$route/$argument")
        CitiesScreens.MainScreen -> navController.navigate(route)
    }
}