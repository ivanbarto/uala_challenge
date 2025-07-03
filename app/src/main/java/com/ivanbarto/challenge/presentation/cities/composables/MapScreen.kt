package com.ivanbarto.challenge.presentation.cities.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ivanbarto.domain_cities.City
import org.koin.core.time.inMs
import kotlin.time.Duration.Companion.seconds


@Composable
fun MapScreen(city: City?, modifier: Modifier) {
    val pinPosition =
        LatLng(
            city?.coordinate?.lat?.toDouble() ?: 0.0,
            city?.coordinate?.lon?.toDouble() ?: 0.0
        )

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(city) {
        city?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(pinPosition, 15f),
                durationMs = 2.seconds.inMs.toInt()
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        city?.let {
            Marker(
                state = MarkerState(position = pinPosition),
                title = "${city.name}, ${city.country}"
            )
        }
    }
}