package com.ivanbarto.challenge.presentation.cities.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ivanbarto.challenge.tools.TestTag
import com.ivanbarto.domain_cities.City

private const val CAMERA_ZOOM = 15f
private const val CAMERA_ANIMATION_TIME = 2000

@Composable
fun DynamicMapScreen(city: City?, modifier: Modifier) {
    val pinPosition =
        LatLng(
            city?.coordinate?.lat?.toDouble() ?: 0.0,
            city?.coordinate?.lon?.toDouble() ?: 0.0
        )

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(city) {
        city?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(pinPosition, CAMERA_ZOOM),
                durationMs = CAMERA_ANIMATION_TIME
            )
        }
    }

    GoogleMap(
        modifier = modifier.testTag(TestTag.DYNAMIC_MAP_SCREEN),
        cameraPositionState = cameraPositionState
    ) {
        city?.let {
            CityMarker(pinPosition, city)
        }
    }
}

@Composable
fun StaticMapScreen(city: City, modifier: Modifier) {
    val pinPosition =
        LatLng(
            city.coordinate.lat.toDouble(),
            city.coordinate.lon.toDouble()
        )

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(city) {
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(pinPosition, CAMERA_ZOOM))
    }

    GoogleMap(
        modifier = modifier.testTag(TestTag.STATIC_MAP_SCREEN),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false
        )
    ) {
        CityMarker(pinPosition, city)
    }
}

@Composable
private fun CityMarker(
    pinPosition: LatLng,
    city: City
) {
    Marker(
        state = MarkerState(position = pinPosition),
        title = city.toString()
    )
}