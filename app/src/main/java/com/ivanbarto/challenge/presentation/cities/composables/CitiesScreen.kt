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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.ivanbarto.challenge.presentation.cities.viewModels.CitiesViewModel
import com.ivanbarto.challenge.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CitiesScreen() {
    val viewModel: CitiesViewModel = koinViewModel()

    val state = viewModel.cities.collectAsState().value
    val cityNameFilter = viewModel.cityNameFilter.collectAsState().value
    val filterFavorites = viewModel.filterFavorites.collectAsState().value
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
                    value = cityNameFilter,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { text ->
                        viewModel.filterCityByName(text)
                    }
                )
            }
            stickyHeader {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Switch(
                        checked = filterFavorites,
                        onCheckedChange = {
                            viewModel.filterFavorites(filterFavorites.not())
                        }
                    )
                    Text(text = "Show Only Favorites", style = Typography.bodyMedium)
                }
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