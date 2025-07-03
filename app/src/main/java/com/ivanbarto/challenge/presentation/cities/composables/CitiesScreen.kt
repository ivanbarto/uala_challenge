package com.ivanbarto.challenge.presentation.cities.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.ivanbarto.challenge.presentation.cities.viewModels.CitiesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CitiesScreen() {
    val viewModel: CitiesViewModel = koinViewModel()

    val state = viewModel.cities.collectAsState().value
    val filter = viewModel.cityNameFilter.collectAsState().value
    val selectedCity = viewModel.selectedCity.collectAsState().value

    val localConfiguration = LocalConfiguration.current

    Row {
        LazyColumn(
            modifier = Modifier
                .width(0.dp)
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            stickyHeader {
                TextField(
                    value = filter,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { text ->
                        viewModel.filterCityByName(text)
                    }
                )
            }
            items(state) { city ->
                CityItem(
                    city = city,
                    onClick = {
                        viewModel.selectCity(it)
                    },
                    onSeeDetails = {

                    },
                    onMarkAsFavorite = {
                        viewModel.markAsFavorite(city)
                    }
                )
            }
        }

        if (localConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MapScreen(
                city = selectedCity, modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}