package com.hamza.cityweatherdetails.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hamza.cityweatherdetails.presentation.CurrentWeatherUiState
import com.hamza.core.utils.DateFormatter.getCurrentDateTime
import com.hamza.weatherutils.utils.WeatherUtils

@Composable
fun CurrentWeatherCard(weatherData: CurrentWeatherUiState) {
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
                        text = WeatherUtils.formatTemperature(weatherData.temperature),
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