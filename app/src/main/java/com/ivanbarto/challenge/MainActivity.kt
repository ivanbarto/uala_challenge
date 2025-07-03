package com.ivanbarto.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ivanbarto.challenge.presentation.cities.composables.CitiesScreen
import com.ivanbarto.challenge.presentation.cities.navigation.ScreenNavigation
import com.ivanbarto.challenge.ui.theme.ChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChallengeTheme {
                val navHostController = rememberNavController()

                ScreenNavigation(navHostController = navHostController)
            }
        }
    }
}