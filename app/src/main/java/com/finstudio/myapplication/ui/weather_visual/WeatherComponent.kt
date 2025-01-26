package com.finstudio.myapplication.ui.weather_visual

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.finstudio.myapplication.ui.theme.WeatherAppTheme


@Composable
fun WeatherComponent(modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel = hiltViewModel()){

    val weatherData by weatherViewModel.weatherData.collectAsState()



    val longitude = 0.0
    val latitude = 0.0
    val units = "metrics"

    val fetchKey = remember(longitude, latitude, units) { "$longitude,$latitude,$units" }


    LaunchedEffect(fetchKey) {
        weatherViewModel.fetchWeatherData(longitude = 36.7391, latitude = -1.2817, units = "metrics")
    }

    Text(text = "Hello Location name: ${weatherData?.name}!" ,
        modifier = modifier)


}




@Preview(showBackground = true)
@Composable
fun WeatherComponentPreview(){
    WeatherAppTheme{
            WeatherComponent()
    }

}