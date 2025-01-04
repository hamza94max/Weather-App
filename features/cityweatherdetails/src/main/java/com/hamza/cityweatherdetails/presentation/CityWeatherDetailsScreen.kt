package com.hamza.cityweatherdetails.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hamza.cityweatherdetails.R
import com.hamza.cityweatherdetails.presentation.components.DailyForecastItem
import com.hamza.cityweatherdetails.presentation.components.NoDataFound
import com.hamza.core.utils.DateFormatter.getCurrentDateTime
import com.hamza.core.utils.DateFormatter.getDayName
import com.hamza.data.remote.models.forecasts.DailyForecastData


@Composable
fun CityWeatherDetailsScreen(
    onClick: () -> Unit,
    viewModel: CityWeatherViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeather()
        viewModel.fetchSevenForecasts()
    }

    val currentWeatherUiState by viewModel.currentWeatherUiState.collectAsState()
    val forecastsUiState by viewModel.sevenForecastsUiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(10.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(Icons.Filled.LocationOn, "Location")
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Cairo",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(5.dp))
                Spacer(modifier = Modifier.weight(1f))

                // Uncomment and implement if needed
                // var isDarkTheme = false
                // Switch(
                //     checked = isDarkTheme,
                //     onCheckedChange = { darkTheme ->
                //         isDarkTheme = darkTheme
                //     },
                //     modifier = Modifier
                //         .padding(8.dp)
                //         .height(20.dp)
                //         .width(60.dp)
                //         .clip(RoundedCornerShape(200.dp))
                // )
            }
        }

        item {
            when {
                currentWeatherUiState.isLoading -> CircularProgressIndicator()
                currentWeatherUiState.error != null -> Text(text = "Error: ${currentWeatherUiState.error}")
                else -> {
                    if (currentWeatherUiState.condition.isNotEmpty()) {
                        WeatherCard(currentWeatherUiState)
                    } else {
                        NoDataFound(viewModel, stringResource(R.string.no_current_conditions_found))
                    }
                }
            }
        }

        item {
            when {
                forecastsUiState.isLoading -> CircularProgressIndicator()
                forecastsUiState.error != null -> Text(text = "Error: ${forecastsUiState.error}")
                else -> {
                    if (forecastsUiState.dailyForecastsData.isNotEmpty()) {
                        Column {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = stringResource(R.string.daily_forecasts),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    } else {
                        NoDataFound(viewModel, "No daily forecasts found")
                    }
                }
            }
        }
        items(forecastsUiState.dailyForecastsData.distinctBy { getDayName(it.dt_txt)  }) { item: DailyForecastData ->
            DailyForecastItem(item) {
            }
        }
    }


}

@Composable
fun WeatherCard(weatherData: CurrentWeatherUiState) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xBA191970),
                Color(0xE8000033)
            )
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopEnd)
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,
                                Color.Transparent
                            ),
                            center = center,
                            radius = size.width
                        ),
                        center = center,
                        radius = size.width
                    )
                }

            }
            Box(modifier = Modifier.background(color = Color.Black.copy(alpha = 0.10f))) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = weatherData.condition,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${weatherData.temperature}°",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = getCurrentDateTime(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }

        }
    }
}


