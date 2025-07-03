package com.ivanbarto.challenge.presentation.cities.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun ScreenNavigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = NavGraph.CITIES,
        enterTransition = {
            fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500))
        }
    ) {
        citiesNavigation(navHostController)
    }
}

object NavGraph {
    const val CITIES = "cities_graph"
}