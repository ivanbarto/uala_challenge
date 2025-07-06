package com.ivanbarto.challenge.presentation.cities.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ivanbarto.challenge.R
import com.ivanbarto.challenge.ui.theme.Purple40
import com.ivanbarto.challenge.ui.theme.Typography
import com.ivanbarto.domain_cities.City

@Composable
fun CityItem(
    city: City,
    onClick: (city: City) -> Unit,
    onSeeDetails: () -> Unit,
    onMarkAsFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
            .clickable {
                onClick.invoke(city)
            },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CityTitle(city = city)
        CityCoordinates(city = city)

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
            MarkAsFavoriteButton(city.savedAsFavourite, onMarkAsFavorite)
            SeeDetailsButton(onSeeDetails)
        }
    }
}

@Composable
fun CityTitle(city: City) {
    Text(text = city.toString(), style = Typography.titleMedium)
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
fun SeeDetailsButton(onSeeDetails: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Purple40),
        onClick = {
            onSeeDetails.invoke()
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Text("See Details")
    }
}

@Composable
fun MarkAsFavoriteButton(isMarked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(color = Color.White, shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                onClick.invoke()
            }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(if (isMarked) R.drawable.ic_favorite_filled else R.drawable.ic_favorite),
            colorFilter = ColorFilter.tint(color = Color.Red),
            modifier = Modifier.size(24.dp),
            contentDescription = if (isMarked) "Unmark Favorite" else "Mark as Favorite"
        )
    }
}