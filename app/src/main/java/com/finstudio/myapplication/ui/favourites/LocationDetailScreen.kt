package com.finstudio.myapplication.ui.favourites


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.finstudio.myapplication.utils.WeatherVisualize

@Composable
fun LocationDetailScreen(longitude: Double?, latitude: Double?, locationId: String?, favouriteViewModel: FavouriteViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = locationId) {
        if (longitude != null && latitude != null) {
            favouriteViewModel.fetchWeatherData(longitude, latitude, "metric")
        }
    }

    val activeWeather = favouriteViewModel.activeWeather.collectAsState().value
    val savedWeatherData = favouriteViewModel.savedWeatherData.collectAsState().value


    WeatherVisualize(
        weatherData = activeWeather,
        forecastList = savedWeatherData
    )

}