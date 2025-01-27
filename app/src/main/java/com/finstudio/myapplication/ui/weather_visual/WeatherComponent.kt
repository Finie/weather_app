package com.finstudio.myapplication.ui.weather_visual


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.finstudio.myapplication.data.database.entity.LocationData
import com.finstudio.myapplication.data.database.entity.WeatherData
import com.finstudio.myapplication.ui.map.LocationViewModel
import com.finstudio.myapplication.ui.theme.WeatherAppTheme
import com.finstudio.myapplication.utils.WeatherVisualize
import java.util.UUID




@Composable
fun WeatherComponent(
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel()
) {


    val weatherData by weatherViewModel.activeWeather.collectAsState(
        initial = WeatherData(
            id = UUID.randomUUID(),
            fellsLike = 0.0,
            minTemp = 0.0,
            maxTemp = 0.0,
            weather = "",
            condition = "",
            locationId = null,
            isActive = false
        )
    )
    val activeLocation by weatherViewModel.activeLocation.collectAsState(
        initial = LocationData(
            id = UUID.randomUUID(),
            longitude = 0.0,
            latitude = 0.0,
            name = "",
            isFavourite = false,
            active = false
        )
    )

    val forecastDataList by weatherViewModel.forecastData.collectAsState(emptyList())


    val currentLocation by locationViewModel.location.collectAsState()


    val longitude = 0.0
    val latitude = 0.0
    val units = "metric"

    val fetchKey = remember(longitude, latitude, units) { "$longitude,$latitude,$units" }

    LaunchedEffect(fetchKey) {
        weatherViewModel.fetchWeatherData(longitude = 36.7391, latitude = -1.2817, units = "metric")
    }

    if (activeLocation == null && weatherData == null) {

        Text("Loading...")
        return
    }


    if (weatherData?.weather?.isEmpty() == true
        || activeLocation?.name?.isEmpty() == true
        || forecastDataList.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Render WeatherVisualize when data is available
        WeatherVisualize(
            weatherData = weatherData,
            forecastList = forecastDataList
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherComponentPreview() {
    WeatherAppTheme {
        WeatherComponent()
    }
}