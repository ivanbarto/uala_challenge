package com.ivanbarto.challenge.presentation.cities.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ivanbarto.challenge.R
import com.ivanbarto.challenge.presentation.base.UiState
import com.ivanbarto.challenge.presentation.cities.navigation.CitiesScreens
import com.ivanbarto.challenge.presentation.cities.navigation.navigate
import com.ivanbarto.challenge.presentation.cities.viewModels.CitiesViewModel
import com.ivanbarto.challenge.tools.TestTag
import com.ivanbarto.challenge.ui.theme.Dimensions
import com.ivanbarto.challenge.ui.theme.Purple40
import com.ivanbarto.challenge.ui.theme.PurpleBackground
import com.ivanbarto.challenge.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(navController: NavController) {
    val viewModel: CitiesViewModel = koinViewModel()

    val state = viewModel.cities.collectAsLazyPagingItems()
    val screenState = viewModel.uiState.collectAsState().value
    val cityNameFilter = viewModel.cityNameFilter.collectAsState().value
    val filterFavorites = viewModel.filterFavorites.collectAsState().value
    val selectedCity = viewModel.selectedCity.collectAsState().value

    val localConfiguration = LocalConfiguration.current

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .background(PurpleBackground)
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Row {
                LazyColumn(
                    modifier = Modifier
                        .testTag(TestTag.CITY_LIST)
                        .width(0.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = Dimensions.paddingSmall),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall)
                ) {
                    stickyHeader {
                        SearchBar(
                            colors = SearchBarDefaults.colors(containerColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Dimensions.paddingXLarge)
                                .testTag(TestTag.CITY_SEARCH_BAR),
                            inputField = {
                                SearchBarDefaults.InputField(
                                    query = cityNameFilter,
                                    onQueryChange = {
                                        viewModel.filterCityByName(it)
                                        state.refresh()
                                    },
                                    onSearch = { },
                                    expanded = false,
                                    onExpandedChange = { },
                                    placeholder = {
                                        Text(
                                            stringResource(R.string.text_search_city),
                                            style = Typography.bodyMedium
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null
                                        )
                                    },
                                    trailingIcon = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                colors = CheckboxDefaults.colors(checkedColor = Purple40),
                                                modifier = Modifier.testTag(TestTag.CITY_FILTER_CHECK_BOX),
                                                checked = filterFavorites,
                                                onCheckedChange = {
                                                    viewModel.filterFavorites(filterFavorites.not())
                                                    state.refresh()
                                                }
                                            )
                                            Text(
                                                modifier = Modifier.padding(end = Dimensions.paddingSmall),
                                                text = stringResource(R.string.text_favorites),
                                                style = Typography.bodyMedium.copy(
                                                    color = if (filterFavorites) Purple40 else Typography.bodyMedium.color
                                                )
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

                    items(state.itemCount) { index ->
                        state[index]?.let { city ->
                            CityItem(
                                city = city,
                                isSelected = selectedCity?.id == city.id,
                                onClick = {
                                    viewModel.selectCity(it)
                                },
                                onSeeDetails = {
                                    CitiesScreens.CityDetails.navigate(
                                        navController = navController,
                                        argument = city.id
                                    )
                                },
                                onMarkAsFavorite = {
                                    viewModel.markAsFavorite(city)
                                    state.refresh()
                                }
                            )
                        }
                    }
                }

                if (localConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    DynamicMapScreen(
                        city = selectedCity, modifier = Modifier
                            .width(0.dp)
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
            if (screenState == UiState.LOADING ||
                state.loadState.append == LoadState.Loading ||
                state.loadState.refresh == LoadState.Loading
            ) {
                CircularProgressIndicator(color = Purple40)
            }
        }
    }
}