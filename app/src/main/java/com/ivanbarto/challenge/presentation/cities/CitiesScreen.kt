package com.ivanbarto.challenge.presentation.cities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ivanbarto.challenge.R
import com.ivanbarto.challenge.ui.theme.Typography
import com.ivanbarto.domain.cities.models.City
import org.koin.androidx.compose.koinViewModel

@Composable
fun CitiesScreen() {
    val viewModel: CitiesViewModel = koinViewModel()

    val state = viewModel.cities.collectAsState().value
    val filter = viewModel.cityNameFilter.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            TextField(
                value = filter,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { text ->
                    viewModel.filterCityByName(text)
                }
            )
        }
        items(state) { city ->
            CityItem(city = city)
        }
    }
}

@Composable
fun CityItem(city: City) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CityTitle(city = city)
        CityCoordinates(city = city)
        SeeDetailsButton(city = city)
    }
}

@Composable
fun CityTitle(city: City) {
    Text(text = "${city.name}, ${city.country}", style = Typography.titleMedium)
}

@Composable
fun CityCoordinates(city: City) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_map_pin),
            modifier = Modifier.size(18.dp),
            contentDescription = null
        )
        Text(text = "${city.coordinate.lat}, ${city.coordinate.lat}", style = Typography.bodyMedium)
    }
}

@Composable
fun SeeDetailsButton(city: City) {
    Row(modifier = Modifier) {
        Spacer(Modifier
            .width(0.dp)
            .weight(1f))
        Button(
            onClick = {

            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("See Details")
        }
    }
}