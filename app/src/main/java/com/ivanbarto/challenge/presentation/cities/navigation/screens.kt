package com.ivanbarto.challenge.presentation.cities.navigation

sealed class CitiesScreens(
    val route: String
) {
    object MainScreen : CitiesScreens(route = "main")
    object CityDetails : CitiesScreens(route = "cityDetails")
}