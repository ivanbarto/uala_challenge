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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
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
                SearchBar(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = cityNameFilter,
                            onQueryChange = { viewModel.filterCityByName(it) },
                            onSearch = { },
                            expanded = false,
                            onExpandedChange = { },
                            placeholder = { Text("Search a city", style = Typography.bodyMedium) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = filterFavorites,
                                        onCheckedChange = {
                                            viewModel.filterFavorites(filterFavorites.not())
                                        }
                                    )
                                    Text(
                                        modifier = Modifier.padding(end = 8.dp),
                                        text = "Favorites",
                                        style = Typography.bodyMedium
                                    )
                                }
                            },
                        )
                    },
                    expanded = false,
                    onExpandedChange = {},
                    content = {}
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