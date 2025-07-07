package com.ivanbarto.challenge.presentation.cities.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ivanbarto.challenge.R
import com.ivanbarto.challenge.presentation.cities.viewModels.CityDetailsViewModel
import com.ivanbarto.challenge.ui.theme.Dimensions
import com.ivanbarto.challenge.ui.theme.PurpleBackground
import com.ivanbarto.challenge.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityDetailsScreen(navController: NavController, cityId: String) {
    val viewModel: CityDetailsViewModel = koinViewModel()

    val city = viewModel.cityDetails.collectAsState().value

    val localConfiguration = LocalConfiguration.current

    LaunchedEffect(Unit) {
        viewModel.getCityDetails(cityId)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .background(PurpleBackground)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimensions.paddingLarge)
            ) {
                Box(contentAlignment = Alignment.TopStart) {
                    StaticMapScreen(
                        city = city,
                        modifier = Modifier
                            .height(localConfiguration.screenHeightDp.dp / 3)
                            .fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .padding(Dimensions.paddingSmall)
                            .background(color = Color.White, shape = CircleShape)
                            .clip(CircleShape)
                            .clickable {
                                navController.navigateUp()
                            }
                            .padding(Dimensions.paddingXSmall),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left),
                            colorFilter = ColorFilter.tint(color = Color.Gray),
                            modifier = Modifier.size(Dimensions.iconSizeLarge),
                            contentDescription = stringResource(R.string.text_back_home)
                        )
                    }
                }

                Text(text = city.toString(), style = Typography.titleLarge)
            }
        }
    }

}