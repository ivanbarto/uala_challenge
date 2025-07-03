package com.ivanbarto.challenge.presentation.cities.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ivanbarto.challenge.presentation.cities.composables.CitiesScreen
import com.ivanbarto.challenge.presentation.cities.composables.CityDetailsScreen

fun NavGraphBuilder.citiesNavigation(navController: NavHostController) {
    navigation(
        route = NavGraph.CITIES,
        startDestination = CitiesScreens.MainScreen.route
    ) {
        composable(CitiesScreens.MainScreen.route) {
            CitiesScreen()
        }
        composable(CitiesScreens.CityDetails.route) {
            CityDetailsScreen()
        }
    }
}